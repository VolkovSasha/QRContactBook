And /I enter "([^\"]*)" as the newPhone/ do |edit_text_input_dialog|
  query "edittext", :setText => edit_text_input_dialog
end

Then /I Enter "([^\"]*)" as the newContactBase/ do |contact|
  query "EditText", :setText => contact
end
