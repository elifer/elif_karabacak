# n11 Load Test using Jmeter

### Installation
- Download the N11LoadTest.jmx file.
- Launch Apache JMeter. In the JMeter GUI, go to the top menu and click: File > Open. Select the .jmx file and click Open.
- To run the test, click the Start button.

## Test Cases
### Search Performance
Purpose: To measure the system response time when a user performs a search.

Steps:
Open the home page.
Type a keyword in the search box (example: "phone").
Click the "Search" button.

Expected Result: The search result page should load quickly (example: < 2 seconds).

### Search With Invalid Input
Purpose: To test how the search system behaves against invalid entries.

Steps:
Type special characters (example: "###&*") in the search box.
Click the "Search" button.

Expected Result:
The system should quickly display the message "No results found" in the search result.

### Search With Filter
Purpose: Measure filtering and sorting performance.

Steps:
Search and apply "price sort" filter.
Add various criteria to filters (e.g. "Brand").

Expected Result:
Filtering operations should be completed quickly.

### Search Iteration Test
Purpose: To test a single user performing continuous searches at short intervals.

Steps:
The same user performs consecutive searches with different keywords (example: "phone", "shoes", "television").

Expected Result:
System performance should not decrease and no errors should be received.
