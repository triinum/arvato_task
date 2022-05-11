@APIFeatures
Feature: API Tests


  Scenario: Bank account validation returns isValid flag = true for valid IBANs
    Given the bank account validation request with a valid IBAN
    And valid authentication token "true"
    When bank account validation request is sent to the server
    Then server responds with HTTP response code 200
    And response body contains isValid "true"


  Scenario: Bank account validation responds with 401 response code, if authentication token was not provided.
    Given the bank account validation request without a JWT token "false"
    When sample request is sent to the server
    Then server responds with HTTP response code 401
    And response body contains "Authorization has been denied for this request." message.
