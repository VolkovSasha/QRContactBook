Feature: Edit Contact Activity

	Scenario: As a user I can click on edit contact activity
		Given I wait for the "HomeActivity" screen to appear
		Then I drag from 99:50 to 50:50 moving with 5 steps
		And I see "contact1"
		Then I press list item number 1
		Then I wait for the "ContactActivity" screen to appear
		Then I press "edit"
		Then I wait for the "EditContactActivity" screen to appear
		And I see "Edit Profile"
		And I see "contact1"
		And I see "VIP"
		And I see "somemail@mail.ru"
		And I see "Mobile"
		And I see "0951435423"
		And I see "Work"
		And I see "0951435423"
		And I see "Add Phone"
		And I see "Save"
		And I see "Cancel"
		
	Scenario: As a user I can in EditContactActivity click  Add Phone
		Given I wait for the "HomeActivity" screen to appear
		Then I drag from 99:50 to 50:50 moving with 5 steps
		And I see "contact1"
		Then I press list item number 1
		Then I wait for the "ContactActivity" screen to appear
		Then I press "edit"
		Then I wait for the "EditContactActivity" screen to appear
		And I see "Add Phone"
		Then I press "Add Phone"
		And I see "Add Phone Number"
		And I see "Mobile"
		And I see "OK"
		And I see "Cancel"
		
	Scenario: As a user I can in EditContactActivity click  Add Phone buttons and add Phone
		Given I wait for the "HomeActivity" screen to appear
		Then I drag from 99:50 to 50:50 moving with 5 steps
		And I see "contact3"
		Then I press list item number 3
		Then I wait for the "ContactActivity" screen to appear
		Then I press "edit"
		Then I wait for the "EditContactActivity" screen to appear
		And I see "Add Phone"
		Then I press "Add Phone"
		And I enter "80951474652" as the newPhone
		And I press the "OK" button
		Then I wait for the "EditContactActivity" screen to appear
		And I see "Mobile"
		And I see "80951474652"
		
	Scenario: As a user I can in EditContactActivity click  Add Phone buttons and cancel
		Given I wait for the "HomeActivity" screen to appear
		Then I drag from 99:50 to 50:50 moving with 5 steps
		And I see "contact3"
		Then I press list item number 3
		Then I wait for the "ContactActivity" screen to appear
		Then I press "edit"
		Then I wait for the "EditContactActivity" screen to appear
		And I see "Add Phone"
		Then I press "Add Phone"
		And I enter "80951474652555" as the newPhone
		And I press the "Cancel" button
		And I should not see "80951474652555"		
		
	