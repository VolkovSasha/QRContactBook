Feature: Home Activity
		
	Scenario: As a user I can click on contact and see
		Given I wait for the "HomeActivity" screen to appear
		Then I drag from 99:50 to 50:50 moving with 5 steps
		And I see "contact1"
		Then I press list item number 1
		And I see "View Profile"
		And I see "contact1"
		And I see "VIP"
		And I see "Mobile"
		And I see "Work"
		And I see "E-mail"
		And I see "edit"
		And I see "delete"
		
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
		
		Scenario: As a user I can long click on contact base phone and Generate QR Code for contact
		Given I wait for the "HomeActivity" screen to appear
		And I long press "MyNewContact"
		Then I press "Generate QR Code"
		And I wait for the "QRCoderActivity" screen to appear
		Then I press "Generate"
		Then I wait for 7 seconds