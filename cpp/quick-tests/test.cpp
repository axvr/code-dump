namespace mozilla {

class MyClass : public A
{
  ...
};

class MyClass
  : public X  // When deriving from more than one class, put each on its own line.
  , public Y
{
public:
  MyClass(int aVar, int aVar2)
    : mVar(aVar)
    , mVar2(aVar2)
  {
     ...
  }
  
  // Tiny constructors and destructors can be written on a single line.
  MyClass() { ... }

  // Special member functions, like constructors, that have default bodies should
  // use '= default' annotation instead.
  MyClass() = default;

  // Unless it's a copy or move constructor or you have a specific reason to allow
  // implicit conversions, mark all single-argument constructors explicit.
  explicit MyClass(OtherClass aArg)
  {
    ...
  }

  // This constructor can also take a single argument, so it also needs to be marked
  // explicit.
  explicit MyClass(OtherClass aArg, AnotherClass aArg2 = AnotherClass())
  {
    ...
  }

  int TinyFunction() { return mVar; }  // Tiny functions can be written in a single line.

  int LargerFunction()
  {
    ...
    ...
  }

private:
  int mVar;
};

} // namespace mozilla
