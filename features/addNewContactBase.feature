Feature: Create new contact for base app

	Scenario: As a user I can create new contact in base app
		Given I wait for the "HomeActivity" screen to appear
		Then I drag from 99:50 to 50:50 moving with 5 steps
		When I press the menu key
		And I see "New contact"
		Then I press "New contact"
		And I see "New Contact"
		And I see "Cancel"
		And I see "Add"
		
	Scenario: As a user I can create new contact in base app and press cancel
		Given I wait for the "HomeActivity" screen to appear
		Then I drag from 99:50 to 50:50 moving with 5 steps
		When I press the menu key
		And I see "New contact"
		Then I press "New contact"
		And I see "New Contact"
		Then I Enter "MyNewContact" as the newContactBase
		Then I press "Cancel"
		And I should not see "MyNewContact"
		
	Scenario: As a user I can create new contact in base app and press add
		Given I wait for the "HomeActivity" screen to appear
		Then I drag from 99:50 to 50:50 moving with 5 steps
		When I press the menu key
		And I see "New contact"
		Then I press "New contact"
		And I see "New Contact"
		Then I Enter "MyNewContact" as the newContactBase
		Then I press "Add"
		And I see "MyNewContact"
			