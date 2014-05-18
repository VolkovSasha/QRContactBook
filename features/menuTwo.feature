Feature: Menu Two Testing

	Scenario: As a user I can press key menu_two base contact and see
		Given I wait for the "HomeActivity" screen to appear
		Then I drag from 99:50 to 50:50 moving with 5 steps
		When I press the menu key
		And i see "New contact"
		And I see "Read QR-Code"
		And I see "Export all"
		And I see "Delete all"
		And I see "Settings"

	Scenario: As a user I can press key menu_two New Contact 
		Given I wait for the "HomeActivity" screen to appear
		Then I drag from 99:50 to 50:50 moving with 5 steps
		When I press the menu key
		And I see "New contact"
		Then I press "New contact"
		And I see "New Contact"
		And I see "Cancel"
		And I see "Add"
	
	Scenario: As a user I can press key menu Delete All
		Given I wait for the "HomeActivity" screen to appear
		Then I drag from 99:50 to 50:50 moving with 5 steps
		And I see "contact1"
		And I see "contact2"
		When I press the menu key
		And I see "Delete all"
		Then I press "Delete all"
		And I see "Delete All ?"
		And I see "Cancel"
		And I see "Delete All"
		Then I press "Delete All"
		And I should not see "contact1"
		And I should not see "contact2"	