INSERT INTO project (name,creationDate,creator) VALUES ('Scarab',NOW(),1);
INSERT INTO project (name,creationDate,creator) VALUES ('MyLibrary',NOW(),1);


INSERT INTO issue (subject,description,createdDate,status,reporter,assigned,project) VALUES ('SUBJECT 1','DESCRIPTION DESCRIPTION',NOW(),'O',1,1,2);
INSERT INTO issue (subject,description,createdDate,status,reporter,assigned,project) VALUES ('SUBJECT 2','DESCRIPTION DESCRIPTION',NOW(),'O',1,1,2);
INSERT INTO issue (subject,description,createdDate,status,reporter,assigned,project) VALUES ('SUBJECT 3','DESCRIPTION DESCRIPTION',NOW(),'O',1,1,2);
INSERT INTO issue (subject,description,createdDate,status,reporter,assigned,project) VALUES ('SUBJECT 4','DESCRIPTION DESCRIPTION',NOW(),'O',1,1,2);

INSERT INTO issue (subject,description,createdDate,status,reporter,assigned,project) VALUES ('SUBJECT 5','DESCRIPTION DESCRIPTION',NOW(),'C',1,1,2);
INSERT INTO issue (subject,description,createdDate,status,reporter,assigned,project) VALUES ('SUBJECT 6','DESCRIPTION DESCRIPTION',NOW(),'C',1,1,2);
INSERT INTO issue (subject,description,createdDate,status,reporter,assigned,project) VALUES ('SUBJECT 7','DESCRIPTION DESCRIPTION',NOW(),'C',1,1,2);

INSERT INTO label (label,color) VALUES ('Bug','#F70D1A');
INSERT INTO label (label,color) VALUES ('Enhancement','#7D0552');
