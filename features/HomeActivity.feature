Feature: Home Activity

	Scenario: As a user I can scroll right and left
		Given I wait for the "HomeActivity" screen to appear
		Then I drag from 99:50 to 50:50 moving with 5 steps
		And I drag from 50:50 to 99:50 moving with 5 steps
		
	Scenario: As a user I can see my phone contacts  and my app base contact
		Given I wait for the "HomeActivity" screen to appear
		And I see "trol"
		Then I drag from 99:50 to 50:50 moving with 5 steps
		And I see "contact1"
		And I see "contact2"
		
	Scenario: As a user I can click on contact
		Given I wait for the "HomeActivity" screen to appear
		Then I drag from 99:50 to 50:50 moving with 5 steps
		And I see "contact1"
		Then I press list item number 1
		Then I wait for the "ContactActivity" screen to appear
		
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