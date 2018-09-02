# static-code-analysis
This is a Jenkins plugin to aggregate different reports and measure code quality over several builds. It basically fetches data from other reports (e.g. Checkstyle, PMD) and can monitor "code quality" of a project over time (specifically: x-axis is the build number and y-axis is the number of issues). 



![Static Code Analysis Screenshot](https://i.imgur.com/skpRlSc.png)
So it adds insight to the continuous integration process by showing you roooooughly where the code quality is headed. 

![Static Code Analysis Screenshot](https://i.imgur.com/RYpIUZf.png)

This was part of a group project within a university course (credit given in source) and there a couple issues with that approach, the most obvious one is that it does not take into account how big of an impact an issue is to the user and how difficult it will be to fix/refactor. 

