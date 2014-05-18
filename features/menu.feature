Feature: Menu Testing

	Scenario: As a user I can press key menu tell contact
		Given I wait for the "HomeActivity" screen to appear
		When I press the menu key
		And i see "New Contact"
		And I see "Read QR-Code"
		And I see "Inport all"
		And I see "Settings"
		
	Scenario: As a user I can press key menu base contact
		Given I wait for the "HomeActivity" screen to appear
		Then I drag from 99:50 to 50:50 moving with 5 steps
		When I press the menu key
		And i see "New contact"
		And I see "Read QR-Code"
		And I see "Export all"
		And I see "Delete all"
		And I see "Settings"
