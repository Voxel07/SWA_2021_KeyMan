-- Some initial data
INSERT INTO USER (id, email, username, password, first_name, last_name, is_admin) 
       VALUES ("1","Aemail", "Ausername", "Apassword", "Afirst", "Alast", true);
INSERT INTO USER (id, email, username, password, first_name, last_name,  is_admin)
       VALUES ("2","Bemail", "Busername", "Bpassword", "Bfirst", "Blast", false);
       
       -- Some initial data
INSERT INTO COMPANY (id, name, department, street, postalcode, state, country) 
       VALUES ("1","Aname", "Adepartment", "Astreet", 12345, "Astate", "Acountry");
INSERT INTO COMPANY (id, name, department, street, postalcode, state, country)
       VALUES ("2","Bname", "Bdepartment", "Bstreet", 12345, "Bstate", "Bcountry");
       
              -- Some initial data
INSERT INTO CONTRACT (id, startDate, endDate, version, licenskey) 
       VALUES ("1","1.1.2020", "1.1.2021","ver1", "1234");
INSERT INTO CONTRACT (id, startDate, endDate, version, licenskey)
       VALUES ("2","2.2.2020", "2.2.2021","ver2", "4321");

INSERT INTO PHONE (id, number, type) 
       VALUES (1,"123454","mobile");
INSERT INTO PHONE (id, number, type) 
       VALUES (2, "567892","landline");
