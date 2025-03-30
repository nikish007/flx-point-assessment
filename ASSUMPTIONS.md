# ASSUMPTIONS.md

## Noteworthy Aspects

1. **Remove Duplicates Logic**:  
   All edge cases involving extra spaces (leading, trailing, and in-between) were handled gracefully to ensure clean output.

## Challenges Overcome

1. **Executor Service Familiarity**:  
   Initially unfamiliar with `ExecutorService`, so I had to revisit the concept and its usage patterns to implement the timeout logic properly.

2. **Timeout for Infinite Loop**:  
   Faced difficulty figuring out how to gracefully terminate or timeout an infinite loop. Resolved this by using appropriate concurrency mechanisms and future task handling.

## Assumptions Made

1. **Deadlock Prevention**:  
   To simulate and resolve deadlock, I assumed that simply changing the order in which locks were acquired would suffice for the scope of this task.

---

**Time Invested**: 3 hrs
