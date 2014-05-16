Feature: Item long click contact

	Scenario: As a user I can long click on contact base phone
		Given I wait for the "HomeActivity" screen to appear
		Then I drag from 99:50 to 50:50 moving with 5 steps
		And I long press list item number 4
		And I see "Contact Menu"
		And I see "Generate QR Code"
		And I see "Cancel"
		
	Scenario: As a user I can long click on contact base phone and click cancel
		Given I wait for the "HomeActivity" screen to appear
		Then I drag from 99:50 to 50:50 moving with 5 steps
		And I long press list item number 4
		And I see "Contact Menu"
		And I see "Generate QR Code"
		And I see "Cancel"
		Then I press "Cancel"
		And I should not see "Contact Menu"
		
	Scenario: As a user I can long click on contact base phone and click Generate QR Code
		Given I wait for the "HomeActivity" screen to appear
		Then I drag from 99:50 to 50:50 moving with 5 steps
		And I long press list item number 4
		And I see "Contact Menu"
		And I see "Generate QR Code"
		And I see "Cancel"
		Then I press "Generate QR Code"
		And I wait for the "QRCoderActivity" screen to appear
		Then I see "QR Code for contact: contact3"
		And I see "Generate"
		
	Scenario: As a user I can long click on contact base phone and Generate QR Code for contact
		Given I wait for the "HomeActivity" screen to appear
		Then I drag from 99:50 to 50:50 moving with 5 steps
		And I long press list item number 4
		Then I press "Generate QR Code"
		And I wait for the "QRCoderActivity" screen to appear
		Then I press "Generate"
		