INSERT INTO teachers (id, name, email) VALUES (1, 'Joseph Stalin', 'stalin@school.com');
INSERT INTO teachers (id, name, email) VALUES (2, 'Winston Churchill', 'churchill@school.com');
INSERT INTO teachers (id, name, email) VALUES (3, 'Franklin D. Roosevelt', 'fdr@school.com');
INSERT INTO teachers (id, name, email) VALUES (4, 'Adolf Hitler', 'hitler@school.com');
INSERT INTO teachers (id, name, email) VALUES (5, 'Mao Zedong', 'mao@school.com');
INSERT INTO teachers (id, name, email) VALUES (6, 'Benito Mussolini', 'mussolini@school.com');
INSERT INTO teachers (id, name, email) VALUES (7, 'Harry Truman', 'truman@school.com');
INSERT INTO teachers (id, name, email) VALUES (8, 'John F. Kennedy', 'jfk@school.com');
INSERT INTO teachers (id, name, email) VALUES (9, 'Theodore Roosevelt', 'teddy@school.com');
INSERT INTO teachers (id, name, email) VALUES (10, 'Charles de Gaulle', 'gaulle@school.com');

-- ----------------------------
-- Insert Students (Extreme Metal Artists)
-- ----------------------------
INSERT INTO students (id, name, email) VALUES (1, 'Glen Benton', 'glen@metal.com');
INSERT INTO students (id, name, email) VALUES (2, 'Kerry King', 'kerry@metal.com');
INSERT INTO students (id, name, email) VALUES (3, 'Varg Vikernes', 'varg@metal.com');
INSERT INTO students (id, name, email) VALUES (4, 'Abbath Doom Occulta', 'abbath@metal.com');
INSERT INTO students (id, name, email) VALUES (5, 'Gaahl', 'gaahl@metal.com');
INSERT INTO students (id, name, email) VALUES (6, 'Fenriz', 'fenriz@metal.com');
INSERT INTO students (id, name, email) VALUES (7, 'Nocturno Culto', 'nocturno@metal.com');
INSERT INTO students (id, name, email) VALUES (8, 'Euronymous', 'euronymous@metal.com');
INSERT INTO students (id, name, email) VALUES (9, 'Dead', 'dead@metal.com');
INSERT INTO students (id, name, email) VALUES (10, 'Ihsahn', 'ihsahn@metal.com');
INSERT INTO students (id, name, email) VALUES (11, 'King ov Hell', 'king@metal.com');
INSERT INTO students (id, name, email) VALUES (12, 'Satyr', 'satyr@metal.com');
INSERT INTO students (id, name, email) VALUES (13, 'Frost', 'frost@metal.com');
INSERT INTO students (id, name, email) VALUES (14, 'Shagrath', 'shagrath@metal.com');
INSERT INTO students (id, name, email) VALUES (15, 'Galder', 'galder@metal.com');
INSERT INTO students (id, name, email) VALUES (16, 'Hellhammer', 'hellhammer@metal.com');
INSERT INTO students (id, name, email) VALUES (17, 'Necrobutcher', 'necro@metal.com');
INSERT INTO students (id, name, email) VALUES (18, 'Infernus', 'infernus@metal.com');
INSERT INTO students (id, name, email) VALUES (19, 'Dani Filth', 'dani@metal.com');
INSERT INTO students (id, name, email) VALUES (20, 'Nick Barker', 'barker@metal.com');
INSERT INTO students (id, name, email) VALUES (21, 'Tony Laureano', 'tony@metal.com');
INSERT INTO students (id, name, email) VALUES (22, 'Tomas Haake', 'tomas@metal.com');
INSERT INTO students (id, name, email) VALUES (23, 'Mikael Åkerfeldt', 'mikael@metal.com');
INSERT INTO students (id, name, email) VALUES (24, 'Peter Tägtgren', 'peter@metal.com');
INSERT INTO students (id, name, email) VALUES (25, 'Chuck Schuldiner', 'chuck@metal.com');
INSERT INTO students (id, name, email) VALUES (26, 'Max Cavalera', 'max@metal.com');
INSERT INTO students (id, name, email) VALUES (27, 'Phil Anselmo', 'phil@metal.com');
INSERT INTO students (id, name, email) VALUES (28, 'Barney Greenway', 'barney@metal.com');
INSERT INTO students (id, name, email) VALUES (29, 'Corpsegrinder', 'corpse@metal.com');
INSERT INTO students (id, name, email) VALUES (30, 'Trey Azagthoth', 'trey@metal.com');

-- ----------------------------
-- Insert Classes
-- ----------------------------
INSERT INTO classes (id, title, teacher_id) VALUES (1, 'Intro to Java', 1);
INSERT INTO classes (id, title, teacher_id) VALUES (2, 'Advanced JavaScript', 2);
INSERT INTO classes (id, title, teacher_id) VALUES (3, 'HTML and CSS Basics', 3);
INSERT INTO classes (id, title, teacher_id) VALUES (4, 'Python for Hackers', 4);
INSERT INTO classes (id, title, teacher_id) VALUES (5, 'C++ for Hardware Control', 5);

-- ----------------------------
-- Associate Students with Classes (classes_students)
-- ----------------------------
-- Πρώτοι 10 μαθητές -> Class 1
INSERT INTO classes_students (class_id, student_id) VALUES (1, 1);
INSERT INTO classes_students (class_id, student_id) VALUES (1, 2);
INSERT INTO classes_students (class_id, student_id) VALUES (1, 3);
INSERT INTO classes_students (class_id, student_id) VALUES (1, 4);
INSERT INTO classes_students (class_id, student_id) VALUES (1, 5);
INSERT INTO classes_students (class_id, student_id) VALUES (1, 6);
INSERT INTO classes_students (class_id, student_id) VALUES (1, 7);
INSERT INTO classes_students (class_id, student_id) VALUES (1, 8);
INSERT INTO classes_students (class_id, student_id) VALUES (1, 9);
INSERT INTO classes_students (class_id, student_id) VALUES (1, 10);

-- Επόμενοι 10 -> Class 2
INSERT INTO classes_students (class_id, student_id) VALUES (2, 11);
INSERT INTO classes_students (class_id, student_id) VALUES (2, 12);
INSERT INTO classes_students (class_id, student_id) VALUES (2, 13);
INSERT INTO classes_students (class_id, student_id) VALUES (2, 14);
INSERT INTO classes_students (class_id, student_id) VALUES (2, 15);
INSERT INTO classes_students (class_id, student_id) VALUES (2, 16);
INSERT INTO classes_students (class_id, student_id) VALUES (2, 17);
INSERT INTO classes_students (class_id, student_id) VALUES (2, 18);
INSERT INTO classes_students (class_id, student_id) VALUES (2, 19);
INSERT INTO classes_students (class_id, student_id) VALUES (2, 20);

-- Τελευταίοι 10 -> Class 3
INSERT INTO classes_students (class_id, student_id) VALUES (3, 21);
INSERT INTO classes_students (class_id, student_id) VALUES (3, 22);
INSERT INTO classes_students (class_id, student_id) VALUES (3, 23);
INSERT INTO classes_students (class_id, student_id) VALUES (3, 24);
INSERT INTO classes_students (class_id, student_id) VALUES (3, 25);
INSERT INTO classes_students (class_id, student_id) VALUES (3, 26);
INSERT INTO classes_students (class_id, student_id) VALUES (3, 27);
INSERT INTO classes_students (class_id, student_id) VALUES (3, 28);
INSERT INTO classes_students (class_id, student_id) VALUES (3, 29);
INSERT INTO classes_students (class_id, student_id) VALUES (3, 30);



SELECT setval('students_seq', 30, true);
SELECT setval('teachers_seq', 10, true);
SELECT setval('classes_seq', 5, true);