// open System.Reflection.Emit

// type MyClass1(x: int, y: int) =
//     do printfn "%d %d" x y
//     new() = MyClass1(0, 0)

// let methodArgs = [| typeof<int> |]

// let squareIt =
//     DynamicMethod(
//         "SquareIt",
//         typeof<int64>,
//         methodArgs,
//         typeof<MyClass1>.Module)

// let il = squareIt.GetILGenerator()
// il.Emit(OpCodes.Ldarg_0)
// il.Emit(OpCodes.Conv_I8);
// il.Emit(OpCodes.Dup);
// il.Emit(OpCodes.Mul);
// il.Emit(OpCodes.Ret);

module BasicFunctions =

    /// Hello world!
    let sampleFunction1 x = x * x + 3

    let result1 = sampleFunction1 3

    printfn $"hello{result1}"

    ignore [ 0 .. 9 ]

    ignore [ for i in 0 .. 99 -> (i, i * i) ]

    let tuple1 = (1, 2, 3)

    let swapElems (a, b, c) = (c, b, a)

    printfn $"{swapElems tuple1}"

    let foo =
        [ 0 .. 99 ]
        |> List.map (fun x -> x + 1)
        |> List.filter (fun x -> x % 2 = 0)
        |> List.reduce (fun acc x -> acc + x)

    type 'a stack =
        | EmptyStack
        | StackNode of 'a * 'a stack

    type stack2<'a> =
        | EmptyStack
        | StackNode of 'a * stack2<'a>

    let stack = StackNode(1, StackNode(3, EmptyStack))

let greet name =
    printfn "Hello there, %s" name

[<EntryPoint>]
let main args =
    greet "Alex"
    0
