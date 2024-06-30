module DataStructs

open System.Collections

type ISeq =
    abstract First: unit -> obj
    abstract Next: unit -> ISeq

/// Interface for self reducing collection types.
type IReducible =
    abstract Reduce: reducingFn: ('acc * 'elm -> 'acc) * init: 'elm -> 'acc

type ITransientColl<'T> =
    abstract Conj: elm: obj -> #ITransientColl<'T>
    abstract Freeze: unit -> 'T
and IMutableColl<'T> =
    abstract Unfreeze: unit -> #ITransientColl<'T>

type IAssociative =
    abstract Assoc: key: obj * value: obj -> #IAssociative
    abstract Contains: key: obj -> bool

type IDissociative =
    abstract Dissoc: key: obj -> #IDissociative

type ILookup =
    abstract Get: key: obj -> option<obj>
    abstract Get: key: obj * fallback: obj -> obj

type ICountable =
    abstract Count: unit -> uint64

type IIndexed =
    inherit ICountable
    abstract Nth: idx: int -> option<obj>

type IDotNetList =
    inherit IList

type IDotNetMap =
    inherit IDictionary

type IDotNetSet =
    inherit Generic.ISet<obj>

// TODO: case insensitive maps for HTTP headers.

// type KeyValuePair =
//     KeyValuePair | DictionaryEntry

type IEmpty<'T> =
    /// Returns an empty version of the implementing collection.
    abstract Empty: unit -> 'T

type IPersistentColl =
    inherit ICountable
    inherit ICollection  // or IEnumerable ???
    inherit ILookup

type IPersistentVector =
    inherit ICountable
    inherit IEmpty<IPersistentVector>
    inherit ILookup
    inherit IMutableColl<IPersistentVector>
    inherit IReducible
    inherit ISeq

type Op =
    static member Count (c: ICountable) = c.Count()
    static member Count (c: ICollection) = uint64 c.Count
    static member Count (s: string) = uint64 s.Length
    static member Count (o: obj) =
        tryUnbox<ICountable>

    static member Assoc (m: IAssociative) k v = m.Assoc(k, v)
    static member Assoc (m: IDictionary) k v = m.Add(k, v); m

    static member Conj (hs: Generic.ISet<'T>) itm =
        hs.Add(itm) |> ignore; hs
    static member Conj (hs: ICollection) = ()

    static member Reduce f init (coll: IReducible) =
        coll.Reduce(f, init)

let foo =
    [| 1; 2; 3 |]
    :> obj
    |> tryUnbox<ICollection>
    |> Op.Count

let bar = Op.Reduce (fun acc e -> acc + e) 0 (1, 2, 3)

type PersistentVector =
    interface IPersistentVector with
        member this.Count()
