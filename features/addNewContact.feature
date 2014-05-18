Feature: Create new contact for Phone

	Scenario: As a user I can press key menu tell contact and see
		Given I wait for the "HomeActivity" screen to appear
		When I press the menu key
		And I see "New Contact"
		And I see "Read QR-Code"
		And I see "Inport all"
		And I see "Settings"
		
	Scenario: As a user I can create new contact
		Given I wait for the "HomeActivity" screen to appear
		When I press the menu key
		And I see "New Contact"
		Then I press "New Contact"
		And I see "New Contact"
		And I see "Name:"
		And I see "Number:"
		And I see "Cancel"
		And I see "Add"
	
	Scenario: As a user I can create new contact and press cancel
		Given I wait for the "HomeActivity" screen to appear
		When I press the menu key
		And I see "New Contact"
		Then I press "New Contact"
		And I see "New Contact"
		Then I enter "MyNewContact" into input field number 1
		Then I enter "0951477898" into input field number 2
		Then I press "Cancel"
		
	Scenario: As a user I can create new contact and press add
		Given I wait for the "HomeActivity" screen to appear
		When I press the menu key
		And I see "New Contact"
		Then I press "New Contact"
		And I see "New Contact"
		Then I enter "MyNewContact" into input field number 1
		Then I enter "0951477898" into input field number 2
		Then I press "Add"
		And I see "MyNewContact"