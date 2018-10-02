Feature: Create and get registries of todo's in database

  @Todo
  Scenario: A create of a todo in database occurred with success
    Given the todo name MyFirstTodo
    And the description My todo to do something
    And the priority 3
    And the author Aragorn
    When create a todo
    Then should return a status code 200
    And return todo created

  @Todo
  Scenario: A create of a todo in database occurred with error because name not was informed
    Given the todo without name
    And the description My todo to do something
    And the priority 3
    And the author Aragorn
    When create a todo
    Then should return a status code 400

  @Todo
  Scenario: A create of a todo in database occurred with error because description not was informed
    Given the todo name MyFirstTodo
    And without description
    And the priority 3
    And the author Aragorn
    When create a todo
    Then should return a status code 400

  @Todo
  Scenario: A create of a todo in database occurred with error because priority not was informed
    Given the todo name MyFirstTodo
    And the description My todo to do something
    And without priority
    And the author Aragorn
    When create a todo
    Then should return a status code 400

  @Todo
  Scenario: A create of a todo in database occurred with error because priority is incorrect
    Given the todo name MyFirstTodo
    And the description My todo to do something
    And the priority 0
    And the author Aragorn
    When create a todo
    Then should return a status code 400

  @Todo
  Scenario: A create of a todo in database occurred with error because priority is incorrect
    Given the todo name MyFirstTodo
    And the description My todo to do something
    And the priority 6
    And the author Aragorn
    When create a todo
    Then should return a status code 400

  @Todo
  Scenario: A create of a todo in database occurred with error because priority is incorrect
    Given the todo name MyFirstTodo
    And the description My todo to do something
    And the priority 4
    And without author
    When create a todo
    Then should return a status code 400

  @Todo
  Scenario: A get of a todo from database occurred with success
    Given the todo id 1
    When get a todo
    Then should return a status code 200
    And return todo founded

  @Todo
  Scenario: A get of a todo from database occurred with error because id is incorrect
    Given without todo id
    When get a todo
    Then should return a status code 400


  @Todo
  Scenario: A get of a todo from database occurred error because id is incorrect
    Given the todo id 9999
    When get a todo
    Then should return a status code 500
