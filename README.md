# Phoenix Project Selenium Test Suite

This project contains simplified automated tests for the Phoenix Trello application using Selenium WebDriver and the Page Object Pattern.

## Project Structure

- `src/test/java/pages/` - Page Object classes
- `src/test/java/tests/` - Test classes
- `src/test/java/Test1Test.java` - Original test converted to use Page Object Pattern

## Page Objects

The following page objects have been implemented:

- `BasePage` - Base class for all page objects with common methods
- `HomePage` - Page object for the homepage
- `BoardCreationPage` - Page object for the board creation screen
- `BoardPage` - Page object for a board view
- `CardDetailPage` - Page object for card details view
- `SignInPage` - Page object for the sign-in page
- `SignUpPage` - Page object for the sign-up page

## Test Classes (Simplified)

- `BaseTest` - Base test class with common setup and teardown methods
- `BoardTests` - Simple tests for board creation
- `CardTests` - Basic tests for card creation and viewing
- `WorkflowTests` - Simplified workflow tests
- `AdvancedTests` - Basic tests for keyboard input
- `SimpleTest` - Very basic page load test
- `SignInTest` - Basic sign-in test
- `SignUpTest` - Basic sign-up test
- `IntegratedTest` - Integration tests with Phoenix application
- `Test1Test` - Original test converted to use the Page Object Pattern

## Running the Tests

### Prerequisites

1. Java JDK 8 or higher
2. Maven
3. Firefox browser
4. Geckodriver configured at `/snap/bin/geckodriver`

### Command to Run Tests

```bash
mvn test
```

To run a specific test class:

```bash
mvn test -Dtest=BoardTests
```

## Simplification Overview

This test suite has been simplified to:

1. Reduce test complexity
2. Focus on core functionality
3. Eliminate redundant assertions
4. Make tests more maintainable
5. Isolate tests to specific functionality

### Simplification Details

- **Removed complex assertions** - Simplified assertions to focus on key functionality
- **Reduced test dependencies** - Each test now stands on its own
- **Eliminated complex workflows** - Each test focuses on one specific aspect
- **Streamlined page objects** - Removed unused methods and simplified existing ones
- **Improved readability** - Tests now clearly show what's being tested

The test suite implements the Page Object Pattern, which provides the following benefits:

1. **Separation of Concerns**: UI interaction logic is separated from test logic
2. **Reusability**: Page objects can be reused across multiple tests
3. **Maintainability**: Changes to the UI only require updates in one place
4. **Readability**: Tests focus on business logic rather than implementation details

## Advanced Features

- Fluent API pattern for more readable test code
- Support for drag and drop operations
- Card filtering and search functionality
- Accessibility testing examples
- Complex workflows simulating real user scenarios

## Example Test

```java
@Test
public void testCreateBoardWithLists() {
    HomePage homePage = new HomePage(driver);
    BoardPage boardPage = homePage.navigateTo()
                               .createNewBoard("Project Kanban");
    
    // Add lists to the board
    boardPage.createList("To Do")
            .createList("In Progress")
            .createList("Done");
    
    // Assert lists were created
    assertEquals(3, boardPage.getListCount());
    assertEquals("To Do", boardPage.getListName(0));
    assertEquals("In Progress", boardPage.getListName(1));
    assertEquals("Done", boardPage.getListName(2));
}
```
