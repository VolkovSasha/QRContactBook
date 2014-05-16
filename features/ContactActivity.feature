Feature: Contact Activity

	Scenario: As a user I can click on contact and see
		Given I wait for the "HomeActivity" screen to appear
		Then I drag from 99:50 to 50:50 moving with 5 steps
		And I see "contact1"
		Then I press list item number 1
		Then I wait for the "ContactActivity" screen to appear
		And I see "View Profile"
		And I see "contact1"
		And I see "VIP"
		And I see "Mobile"
		And I see "Work"
		And I see "E-mail"
		And I see "edit"
		And I see "delete"
		
	Scenario: As a user I can delete contact from the list
		Given I wait for the "HomeActivity" screen to appear
		Then I drag from 99:50 to 50:50 moving with 5 steps
		And I see "contact1"
		Then I press list item number 1
		Then I wait for the "ContactActivity" screen to appear
		And I see "delete"
		Then I press the "delete" button
		And I see "Delete Contact?"
		Then I press "Delete"
		And I wait for the "HomeActivity" screen to appear
		And I should not see "contact1"		
		
	Scenario: As a user I can cancel delete contact from the list
		Given I wait for the "HomeActivity" screen to appear
		Then I drag from 99:50 to 50:50 moving with 5 steps
		And I see "contact2"
		Then I press list item number 2
		Then I wait for the "ContactActivity" screen to appear
		And I see "delete"
		Then I press the "delete" button
		And I see "Delete Contact?"
		Then I press "Cancel"
		And I wait for the "ContactActivity" screen to appear
		And I see "contact2"	
		
	