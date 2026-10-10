# Design Notes

## Why ArrayList instead of an array?

- An array has a **fixed size**. We cannot know in advance how many students, courses or enrollments
  an admin will add, so we would have to create a bigger array and copy elements by hand when it fills up.
- `ArrayList` **grows automatically** and gives ready-made methods (`add`, `remove`, `size`, `isEmpty`,
  iteration with for-each), which keeps the repository code short and readable.
- Removing an item shifts the remaining elements for us, which would be tedious with a plain array.

## Where static members are used, and why

- `IdGenerator` has `private static int studentIdCounter` (and counters for courses, enrollments and
  trainers) with static methods like `getNextStudentId()`. The counter belongs to the whole application,
  not to one object, so every new student gets a unique ID no matter which service created it.
- `InputValidator` contains only static helper methods (`requireNonEmpty`, `requireValidEmail`,
  `parsePositiveInt`). They need no object state, so there is no reason to create an instance.
- `AppConstants` and `MenuOptions` hold `public static final` constants, so values like menu numbers are
  defined once and have names instead of "magic numbers".
- In `Main`, the services and scanner are static because `main` is static and the app only needs one of each.

## Where inheritance is used, and what we gained

- `Person` is the base class with `id`, `firstName`, `lastName`, `email` and `getDisplayName()`.
- `Student extends Person` (adds `batch` and `active`) and `Trainer extends Person` (adds `expertise`).
- Constructors call `super(...)` to reuse the base-class setup.
- `getDisplayName()` is **overridden**: `Student` appends the batch, `Trainer` adds a "Trainer" prefix and
  expertise. Code that holds a `Person` reference calls the right version automatically (polymorphism).
- Gains: shared fields and getters/setters are written once, there is less duplicated code, and adding a
  new kind of person (for example `Admin`) only needs the extra fields.

## Other design choices

- **Layers:** `entity` (data) -> `repository` (storage) -> `service` (rules) -> `Main` (UI). Each layer has one job,
  so `Main` only shows menus, reads input and calls services.
- **Custom exceptions:** `EntityNotFoundException` and `InvalidInputException` extend `RuntimeException`, so
  service methods stay clean while `Main` catches them and prints a friendly message instead of crashing.
- **Soft delete:** students are deactivated (`active = false`) rather than removed, so enrollment history is kept.
- **Constructor and method overloading:** `Student` has constructors with and without an email, and
  `StudentService.addStudent` has matching overloads.
- **Enums:** `EnrollmentStatus` (ACTIVE, COMPLETED, CANCELLED) prevents typos that a plain String would allow.
