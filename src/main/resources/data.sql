insert into team (id, contact_email, contact_person_name, location, team_name) values (1, 'sales@awin.com', 'Sales Awin', 'N12345', 'Sales');
insert into staff_member (id, email, name, slack_identifier, team_id) values (1,  'sales.awin@awin.com', 'Sales Awin', 'ABC123', 1);
insert into coffee_break_preference (id, requested_date, sub_type, type, staff_id) values (1, CURRENT_TIMESTAMP(), 'tea', 'drink', 1);
insert into coffee_break_preference_details (coffee_break_preference_id, details, details_key) values (1, 'small', 'size');
insert into coffee_break_preference_details (coffee_break_preference_id, details, details_key) values (1, 'black', 'coffee');
insert into coffee_break_preference_details (coffee_break_preference_id, details, details_key) values (1, '1', 'cup');
