module NqSys

open System.Reflection
open System.Runtime.Loader
open System.Runtime

// let sys1 = AssemblyLoadContext("Sys1", true)

// sys1.Load(
//     "/Users/axvr/Projects/Experiments/WowFSharp/bin/Debug/net8.0/WowFSharp.dll")

// sys1.Unload()

// AssemblyLoadContext works per assembly so there will be multiple of them per service.

// TODO: subclass it to extend its capabilities for Enqueue.
// type TestAssemblyLoadContext(name, unloadable) =
//     inherit AssemblyLoadContext(name, unloadable)

type NqAssemblyDependencyResolver(a) =
    inherit AssemblyDependencyResolver(a)

type NqAssemblyLoadContext(name, unloadable) =
    inherit AssemblyLoadContext(name, unloadable)
    let mutable _resolver = AssemblyDependencyResolver("")
    override this.Load(name) = null
