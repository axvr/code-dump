module Gait

open System
// open System.Formats.Cbor

type Documentation = string

type TerminalId = Guid
type Terminal = {
    Id: TerminalId
    Name: string
    Doc: Documentation }

type ModuleId = Guid
type Module = {
    Id: ModuleId
    Name: string
    Ins: Map<TerminalId, Terminal>
    Outs: Map<TerminalId, Terminal>
    Insts: Map<Guid, Module>
    Net: obj
    Doc: Documentation }

type CollectionId = Guid
type Collection = {
    Id: CollectionId
    Name: string
    Modules: Module[]
    Doc: Documentation
    Version: string }
