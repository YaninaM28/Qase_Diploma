# 🚀 Qase UI Automation Framework

![Java](https://img.shields.io/badge/Java-17-orange)
![Maven](https://img.shields.io/badge/Maven-3.5.5-red)
![Selenide](https://img.shields.io/badge/Selenide-7.16.2-green)
![TestNG](https://img.shields.io/badge/TestNG-Framework-red)
![Allure](https://img.shields.io/badge/Allure-Report-blue)
![Jenkins](https://img.shields.io/badge/GitActions+Jenkins-CI-critical)

---
# 🛠 Technology Stack

| Technology               | Purpose |
|--------------------------|---------|
| ☕ Java 17                | Programming language |
| 📦 Maven                 | Build tool |
| 🌐 Selenide              | UI automation |
| ✅ TestNG                 | Test framework |
| 📊 Allure                | Test reporting |
| 📝 Log4j2                | Logging |
| 🔄 GitActions (+Jenkins) | Continuous Integration |

---

# 📋 Test Checklist

## 🚀 Smoke 

| ID     | Test Case                             | Status |
|--------|---------------------------------------|-------|
| TC-001 | Login with correct email and password | ✅     |
| TC-002 | Create Project                        | ✅     |
| TC-003 | Open Project                          | ✅     |
| TC-004 | Create Suite                          | ✅     |
| TC-005 | Create Nested Suite                   | ✅     |
| TC-006 | Create Test Case                      | ✅     |
| TC-007 | Create Test Case with Steps           | ✅ |
| TC-008 | Full End-to-End test                  | ✅ |


---

## 🔄 Regression 

| ID     | Test Case                                            | Status |
|--------|------------------------------------------------------|--|
| 001    | Negative tests for Login page:                       | ✅ |
| TC-1.2 | Login with empty password                            | ✅ |
| TC-1.3 | Login with empty email                               | ✅ |
| TC-1.4 | Login with negative email and password               | ✅ |
|        | ---------------------------------------------------- | |
| TC-009 | Edit Project                                         | ✅ |
| TC-010 | Edit Suite                                           | ✅ |
| TC-011 | Cancel Test Case                                     | ✅ |
| TC-012 | Delete Test Case                                     | ✅ |
| TC-013 | Delete Suite                                         | ✅ |
| TC-014 | Delete Project                                       | ✅ |
| TC-015 | Logout Test                                          | ✅ |

---

## 🚀 API test

| ID         | Test Case                   | Status |
|------------|-----------------------------|-------|
| API-TC-001 | Create Project              | ✅     |
| API-TC-002 | CRUD Test Case              | ✅     |

---

# 🎯 End-to-End Scenario

```text
Login
   ↓
Create Project
   ↓
Open Project
   ↓
Create Suite
   ↓
Create Test Case
   ↓
Add Test Steps
   ↓
Delete Test Case
   ↓
Delete Suite
   ↓
Delete Project
   ↓
Logout
```

---
# 📈 Project Progress

| Task                  | Status |
|-----------------------|--|
| Maven Project         | ✅ |
| Dependencies          | ✅ |
| Project Structure     | ✅ |
| Base Test             | ✅ |
| Driver Configuration  | ✅ |
| Login Page            | ✅ |
| Dashboard Page        | ✅ |
| Project Page          | ✅ |
| Smoke Tests           | ✅ |
| Regression Tests      | ✅ |
| Retry Analyzer        | ✅ |
| Test Listener         | ✅ |
| Logging               | ✅ |
| Allure Report         | ✅ |
| Parallel Execution    | ✅ |
| GitAction Integration | ✅ |

---