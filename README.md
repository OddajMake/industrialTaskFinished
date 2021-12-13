# IndustrialTask
That was the most challenging programming excercise that I had so far but I surely enjoyed it. Not only did I learn a basics of Spring framework, I also brushed up on my html knowledge from my first year, used my sql skills that I learned this semester and wrote Java methods that I have not written before. I will quickly describe every task, my approach and results, there are also comments in the source code.

## Task 1

*1.	On the New Visit page, add a field for selecting a veterinarian from the list of employee*

The hardest part was to start the task and get basic understanding how the program operates. I watched a spring tutorial to get some basics on the framework and then I studied the code, and studied and studied, I tried reverse engineering some methods, I managed to add a new *'vet'* field to *createOrUpdateVisitForm* gaining some intel and understanding of the whole structure.
I was on the right track but for the whole time (approximately a few hours) I have been changing wrong sql file (I think by accident I have been changing mySQL files instead of h2) but after I found out the problem, first 2 tasks went a lot smoother afterwards. 

###### CHANGELOG

1. Added 'vet' field to createOrUpdateVisitForm
2. Added a line with vets' surnames

## Task 2

*2.	On the Owner Information page, add a column with the selected veterinarian to the Pets and Visits table.*

I already done it before finishing task 1 as I found it easier to see if my code works and was easier to debug, at start I used *description* field to store *vet* String but having an additional column helped a lot.

###### CHANGELOG

1. Added a 'vet' field to ownerDetails
2. Added a 'vet' Column to schema.sql REFERENCING vets(last_name)
3. Added a hard coded random vets for already existing visits
4. Added a private 'vet' (String) field to Visit.java as well as getters and setters


## Task 3

*3.	Add the ability to edit the visit (change the date, pet or veterinarian).*

It was actually the last task that I have finished as I found it way easier to implement cancel visit method the way I did it. **A lot** of trial and error went into this method, it left me clueless at times but I managed to come up with a working solution (mostly trying to copy behaviours from pet/edit and visit/new).


###### CHANGELOG

1. Added a *change visit* button to ownerDetails.html
2. Added a public String initChangeVisit to VisitController.java
3. Added a public String processNewVisitFormTEST (oopsie I forgot to change the name of the method) to VisitController.java

## Task 4

*4.	On the Owner Information page, add the functionality to cancel the visit. The cancelled visit should be distinguished from the active ones*

I already did it before task 3. The implementation is not great at a base level as I am just changing the description to *CANCELED* still allowing user to change the visit which should not be possible. At the very least it is clearly visible on the webpage that the visit has been canceled.

###### CHANGELOG
1. Added a *cancel visit* button to ownerDetails.html
2. Added a public String initCancelVisit method to VisitController.java


## Summary

It was a challenging task albeit I really enjoyed it. I felt hopeless and overwhelmed at start but the more I sank into the code the more I started to understand it. My solutions are far from optimal and could be implemented more easily in many ways but I am proud of them as they are solely my implementatons and they work which is most important. I spent about 24 hours during 3 days period working on the code and even though it were not huge changes I felt that I contributed a little bit. I learned a lot and I was really inspired to learn even more, not only Java but also html and sql.

Igor Danieluk




















