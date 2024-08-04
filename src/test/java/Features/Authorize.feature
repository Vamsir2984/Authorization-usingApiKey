Feature: Authorization API Testing

  Scenario: Retrieve 100 images with API key
    Given a valid API key
    When the user requests 100 images
    Then the API should return a successful response
    And the response should contain 100 images

  Scenario: Retrieve images without API key
    When the user requests 65 images without an API key
    Then the API should return a successful response
    And the response should contain a maximum of 10 images
