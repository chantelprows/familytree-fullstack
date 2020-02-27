package Service;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;
import java.util.UUID;

import DataAccess.EventDao;
import DataAccess.PersonDao;
import DataAccess.UserDao;
import DataTransfer.FillRequest;
import DataTransfer.FillResult;
import JsonObjects.Locations;
import Model.Event;
import Model.Person;
import Model.User;

/**
 * populates the server's database with generated data for the specified username.  Generates
 * specified number of generations (default is 4)
 */

public class Filler {

    /**
     * fills generations with new people and events
     *
     * @param r Fill request object
     * @return Result object
     */

    private int eventCount = 0;
    private int personCount = 0;
    private int gen = 0;

    public FillResult fill(FillRequest r) {
        FillResult fr = new FillResult();
        String username = r.getUsername();
        int generations = r.getGenerations();
        UserDao ud = new UserDao();
        User user;
        Person person = new Person();
        PersonDao pd = new PersonDao();
        EventDao ed = new EventDao();

        try {
            String personID = ud.getPersonID(username);
            if (personID == null) {
                throw new SQLException();
            }
            user = ud.getUser(personID);

            ed.deleteEvents(user);
            Set<Person> people = pd.getPeople(user);
            if (people != null) {
                for (Person p : people) {
                    pd.deletePerson(p);
                }
            }

            person.setPersonID(personID);
            person.setDescendant(user.getUsername());
            person.setGender(user.getGender());
            person.setFirstName(user.getFirstName());
            person.setLastName(user.getLastName());

            generate(generations, user.getUsername(), person);
            StringBuilder sb = new StringBuilder();
            sb.append("Successfully added " + personCount + " persons and " + eventCount);
            sb.append(" events to the database.");
            fr.setSuccessMessage(sb.toString());
        }
        catch (SQLException e) {
            fr.setErrorMessage("Invalid username");
        }
        return fr;
    }

    /**
     * recursive helper to fill generations
     */
    private void generate(int generations, String descendant, Person child) throws SQLException {
        PersonDao pd = new PersonDao();
        if (generations < 0) {
            return;
        }
        else {
            Person mom = new Person();
            Person dad = new Person();
            String momID = UUID.randomUUID().toString();
            String dadID = UUID.randomUUID().toString();

            if (generations > 0) {
                mom.setDescendant(descendant);
                mom.setPersonID(momID);
                mom.setSpouse(dadID);
                mom.setGender("f");
                nameCreator(mom);

                dad.setDescendant(descendant);
                dad.setPersonID(dadID);
                dad.setSpouse(momID);
                dad.setGender("m");
                nameCreator(dad);

                child.setMother(momID);
                child.setFather(dadID);
            }
            try {
                pd.addPerson(child);
                eventCreator(descendant, child, gen);
                personCount++;
            }
            catch (SQLException e) {
                System.out.println("couldn't add person");
            }

            gen++;
            generate(generations - 1, descendant, mom);
            generate(generations - 1, descendant, dad);
        }
    }

    private void nameCreator(Person person) {
        String fNamesPath = "C:\\Users\\chant\\AndroidStudioProjects\\FamilyMap\\json\\fnames.json";
        String mNamesPath = "C:\\Users\\chant\\AndroidStudioProjects\\FamilyMap\\json\\mnames.json";
        String sNamesPath = "C:\\Users\\chant\\AndroidStudioProjects\\FamilyMap\\json\\snames.json";
        Gson gson = new Gson();

        try {
            FileReader f = new FileReader(fNamesPath);
            FileReader m = new FileReader(mNamesPath);
            FileReader s = new FileReader(sNamesPath);
            String[] fNames = gson.fromJson(f, String[].class);
            String[] mNames = gson.fromJson(m, String[].class);
            String[] sNames = gson.fromJson(s, String[].class);

            f.close();
            m.close();
            s.close();

            if (person.getGender().equals("f")) {
                person.setFirstName(fNames[personCount]);
            }
            else {
                person.setFirstName(mNames[personCount]);
            }
            person.setLastName(sNames[personCount]);
        }
        catch (FileNotFoundException e){
            System.out.println("file not found");
        }
        catch (IOException e) {}
    }

    private void eventCreator(String descendant, Person person, int generation) {
        EventDao ed = new EventDao();
        PersonDao pd = new PersonDao();
        Event birth = new Event();
        Event death = new Event();
        Event marriage = new Event();
        int birthYear = 2000 - (generation * 25);
        int deathYear = birthYear + 70;
        int marriageYear = birthYear + 25;

        String locationsPath = "C:\\Users\\chant\\AndroidStudioProjects\\FamilyMap\\json\\locations.json";
        Gson gson = new Gson();

        try {
            FileReader l = new FileReader(locationsPath);
            Locations[] locations = gson.fromJson(l, Locations[].class);
            l.close();

            birth.setEventID(UUID.randomUUID().toString());
            birth.setEventType("Birth");
            birth.setYear(birthYear);
            birth.setPerson(person.getPersonID());
            birth.setDescendant(descendant);
            birth.setCity(locations[eventCount].getCity());
            birth.setCountry(locations[eventCount].getCountry());
            birth.setLatitude(locations[eventCount].getLatitude());
            birth.setLongitude(locations[eventCount].getLongitude());

            ed.addEvent(birth);
            eventCount++;

            if (person.getSpouse() != null) {
                Person spouse = pd.getPerson(person.getSpouse());
                boolean hasMarriage = false;
                Event spouseMarriage = new Event();
                if (spouse != null) {
                    Set<Event> spouseEvents = ed.getEvents(spouse.getPersonID());
                    for (Event e : spouseEvents) {
                        if (e.getEventType().equals("Marriage")) {
                            hasMarriage = true;
                            spouseMarriage = e;
                        }
                    }
                }
                    if (hasMarriage) {
                        marriage.setYear(spouseMarriage.getYear());
                        marriage.setCity(spouseMarriage.getCity());
                        marriage.setCountry(spouseMarriage.getCountry());
                        marriage.setLatitude(spouseMarriage.getLatitude());
                        marriage.setLongitude(spouseMarriage.getLongitude());
                    } else {
                        marriage.setYear(marriageYear);
                        marriage.setCity(locations[eventCount].getCity());
                        marriage.setCountry(locations[eventCount].getCountry());
                        marriage.setLatitude(locations[eventCount].getLatitude());
                        marriage.setLongitude(locations[eventCount].getLongitude());
                    }

                    marriage.setEventType("Marriage");
                    marriage.setEventID(UUID.randomUUID().toString());
                    marriage.setPerson(person.getPersonID());
                    marriage.setDescendant(descendant);

                    ed.addEvent(marriage);
                    eventCount++;
            }

            death.setEventID(UUID.randomUUID().toString());
            death.setEventType("Death");
            death.setYear(deathYear);
            death.setPerson(person.getPersonID());
            death.setDescendant(descendant);
            death.setCity(locations[eventCount].getCity());
            death.setCountry(locations[eventCount].getCountry());
            death.setLatitude(locations[eventCount].getLatitude());
            death.setLongitude(locations[eventCount].getLongitude());

            if (gen != 0 ) {
                ed.addEvent(death);
                eventCount++;
            }
        }
        catch (FileNotFoundException e){
            System.out.println("file not found");
        }
        catch (SQLException e) {
            System.out.println("could not add event");
        }
        catch (IOException e) {}
    }
}
