Feature: Menu Testing

	Scenario: As a user I can press key menu tell contact
		Given I wait for the "HomeActivity" screen to appear
		When I press the menu key
		And I see "Read QR-Code"
		And I see "Inport all"
		And I see "Settings"
		
	Scenario: As a user I can press key menu base contact
		Given I wait for the "HomeActivity" screen to appear
		Then I drag from 99:50 to 50:50 moving with 5 steps
		When I press the menu key
		And I see "Read QR-Code"
		And I see "Export all"
		And I see "Delete all"
		And I see "Settings"
		
	Scenario: As a user I can press key menu Read QR-Code
		Given I wait for the "HomeActivity" screen to appear
		When I press the menu key
		And I see "Read QR-Code"
		Then I press "Read QR-Code"
		And I wait for the "QRDecoderActivity" screen to appear
		And I see "Scanning..."
		And I see "Scan"
		And I see "Save"
		
	Scenario: As a user I can press key menu Inpotr All
		Given I wait for the "HomeActivity" screen to appear
		And I see "Lol"
		When I press the menu key
		And I see "Inport all"
		Then I press "Inport all"
		And I see "Inport All"
		And I see "Cansel"
		And I see "Inport"
		Then I press "Inport"
		Then I drag from 99:50 to 50:50 moving with 5 steps
		And I see "Lol"
		
	Scenario: As a user I can press key menu Delete All
		Given I wait for the "HomeActivity" screen to appear
		Then I drag from 99:50 to 50:50 moving with 5 steps
		And I see "contact1"
		And I see "contact2"
		When I press the menu key
		And I see "Delete all"
		Then I press "Delete all"
		And I see "Delete All ?"
		And I see "Cansel"
		And I see "Delete All"
		Then I press "Delete All"
		And I should not see "contact1"
		And I should not see "contact2"	