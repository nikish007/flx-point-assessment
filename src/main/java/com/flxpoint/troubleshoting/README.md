# **Java 17 Troubleshooting & Debugging Assessment** 🚀

## **📌 Overview**
This assessment is designed to evaluate your ability to **identify and fix issues** in a Java 17 application. You will encounter a mix of:

- **Compilation Errors** 🛑
- **Runtime Exceptions** ⚠️
- **Concurrency Issues** 🔄
- **Deadlocks & Resource Leaks** 🕵️‍♂️

Your task is to **debug the provided code, fix the errors, and optimize performance** while maintaining the intended functionality.

---

### **3️⃣ Compile & Run the Code**
```bash
javac TroubleshootingTest.java
java TroubleshootingTest
```
You will encounter compilation errors or runtime exceptions. Your task is to **fix them**.

---

## **📝 Tasks to Complete**
Below are the **types of issues** you need to identify and fix:

### **1️⃣ Compilation Errors (Syntax & Type Safety)**
- ❌ **Method not found** → Some methods are called but don’t exist.
- ❌ **Type mismatch** → Incorrect data types assigned to variables.
- ❌ **Unhandled Exception** → Exceptions that must be caught or declared.

---

### **2️⃣ Runtime Errors (Exceptions & Bugs)**
- ❌ **NullPointerException** → A null object is being accessed.
- ❌ **NumberFormatException** → Invalid string conversion to a number.
- ❌ **ConcurrentModificationException** → A collection is modified by multiple threads.

---

### **3️⃣ Concurrency & Threading Issues**
- ❌ **Race conditions** → Multiple threads modifying shared data without synchronization.
- ❌ **Deadlocks** → Two threads waiting on each other’s locks.

---

### **4️⃣ Performance & Resource Management Issues**
- ❌ **Infinite loops** → Some loops run indefinitely without an exit condition.
- ❌ **Resource leaks** → Scanner objects are not closed properly.

---

## **🎯 Evaluation Criteria**
You will be evaluated based on:  
✔️ **Correctness** → Did you fix all errors?  
✔️ **Code Quality** → Is your code clean, structured, and efficient?  
✔️ **Thread Safety** → Did you fix concurrency issues?  
✔️ **Error Handling** → Are exceptions handled gracefully?  
✔️ **Performance** → Did you optimize resource usage?

---

## **🚀 Bonus Challenge**
Want to go the extra mile?
- **Optimize performance** → Reduce thread contention and improve execution time.
- **Add logging** → Use `java.util.logging` or `SLF4J` for better debugging.
- **Write Unit Tests** → Create JUnit 5 tests to validate fixes.