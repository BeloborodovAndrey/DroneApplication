## Dronees

[[_TOC_]]

---

:scroll: **START**


### Introduction

There is a major new technology that is destined to be a disruptive force in the field of transportation: **the Dronee**. Just as the mobile phone allowed developing countries to leapfrog older technologies for personal communication, the Dronee has the potential to leapfrog traditional transportation infrastructure.

Useful Dronee functions include delivery of small items that are (urgently) needed in locations with difficult access.

---

### Task description

We have a fleet of **10 Dronees**. A Dronee is capable of carrying devices, other than cameras, and capable of delivering small loads. For our use case **the load is medications**.

A **Dronee** has:
- serial number (100 characters max);
- model (Lightweight, Middleweight, Cruiserweight, Heavyweight);
- weight limit (500gr max);
- battery capacity (percentage);
- state (IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING).

Each **Medication** has: 
- name (allowed only letters, numbers, ‘-‘, ‘_’);
- weight;
- code (allowed only upper case letters, underscore and numbers);
- image (picture of the medication case).

Develop a service via REST API that allows clients to communicate with the Dronees (i.e. **dispatch controller**). The specific communicaiton with the Dronee is outside the scope of this task. 

The service should allow:
- registering a Dronee;
- loading a Dronee with medication items;
- checking loaded medication items for a given Dronee; 
- checking available Dronees for loading;
- check Dronee battery level for a given Dronee;

> Feel free to make assumptions for the design approach. 

---

### Requirements

While implementing your solution **please take care of the following requirements**: 

#### Functional requirements

- There is no need for UI;
- Prevent the Dronee from being loaded with more weight that it can carry;
- Prevent the Dronee from being in LOADING state if the battery level is **below 25%**;
- Introduce a periodic task to check Dronees battery levels and create history/audit event log for this.

---

#### Non-functional requirements

- Input/output data must be in JSON format;
- Your project must be buildable and runnable;
- Your project must have a README file with build/run/test instructions (use DB that can be run locally, e.g. in-memory, via container);
- Required data must be preloaded in the database.
- JUnit tests are optional but advisable (if you have time);
- Advice: Show us how you work through your commit history.

---

:scroll: **END** 
