Feature: Reading from file into java objects and vice versa

  Scenario: The file is an xml file
    Given the file "/home/ec2-user/Cucumber/users.xml"
    And the file exists
    Then the second users first name should be Escanor
    And the file "/home/ec2-user/Cucumber/deadly_sins.xml" should exist

  Scenario: the file is a json file
