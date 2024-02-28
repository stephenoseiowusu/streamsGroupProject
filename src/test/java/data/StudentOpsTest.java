package data;

import challenges.StudentOps;
import domain.Student;
import org.assertj.core.api.AssertDelegateTarget;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class StudentOpsTest {


        private static List<Student> students;

        @BeforeAll
        static void setUp() throws IOException {
            // Fetch data before all tests
            students = FetchData.getStudentList();
            students.stream().forEach((student -> {
                student.setAge( new Random().nextInt(13,26));
            }));
        }



        @Test
        void shouldReturnEmptyListWhenNoStudentsMatchGender() {
            List<Student> filteredStudents = StudentOps.filterStudentsByGender(students, "Other");
            assertThat(filteredStudents).isEmpty();
        }

        @Test
        void shouldReturnListStudentsMatchingGender() {
            List<Student> filteredStudents = StudentOps.filterStudentsByGender(students, "Female");
            assertEquals("Female",filteredStudents.get(5).getGender());
        }

        @Test
        void shouldGetAverageAgeOfStudents(){


            int average_age_of_students  = StudentOps.getAverageAgeOfStudents(students);

            assertEquals(18,average_age_of_students);
        }

        @Test
        void shouldPrintAllStudentNames(){
            StudentOps.printAllStudentNames(students);
        }

        @Test
        void shouldGroupStudentsByGender(){
            StudentOps.groupStudentsByGender(students);
        }

        @Test
        void maxAgeShouldBe25(){
            int max_age = StudentOps.getMaxAge(students);
            Assertions.assertEquals(max_age,25);
;        }

        @Test
        void shouldTransformListIntoMap(){
            Object result  = StudentOps.transformListToMap(students);
            Assertions.assertTrue( result instanceof Map<?,?>);
        }

        @Test
        void getAllStudentEmails(){
            List<String> result = StudentOps.getStudentsEmails(students);
            assertThat(result).allMatch((s) ->{
                return s.matches("^(.+)@(.+)$");
            });
        }

        @Test
        void checkIfStudentIsAdult(){
            assertThat(students.get(new Random().nextInt(0,students.size()-1))).matches((s) ->{
                return StudentOps.checkIfStudentISAdult(s);
            });
        }

        @Test
        void checkIfFemaleYoungestStudnetMatchesYoungestFemaleStudent(){
            Optional<Student> student = students.stream().filter((s) ->{
                return s.getGender().equalsIgnoreCase("F") || s.getGender().equalsIgnoreCase("female");
            }).sorted((s1, s2) ->{
                return s1.getAge() - s2.getAge();
            }).findFirst();
            Optional<Student> student2 = StudentOps.getYoungestFemaieStudent(students);
            assertThat(student.get()).isEqualTo(student2.get());
        }

        @Test
        void checkIfAllStudentFirstNameMatchesString(){
            String f
        }


    }

