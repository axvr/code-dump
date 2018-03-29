# PowerShell Script to Easily and Quickly Invoke HTTP Requests using the 
# `Invoke-WebRequest` PowerShell command.

# NOTE This program is not, and will not be completed


[CmdletBinding()]
Param(
        [Parameter(Mandatory=$False,Position=1)]
        [string]$operation
     )


function Start-HTTP-Req() {
    Write-Host "HTTP-Req: A simple and easy to use interface for the PowerShell 'Invoke-WebRequest' command."

    $Method = Get-Method
    $URI    = Get-URI

    Control-Menu

}

# Ask for the HTTP Method to use
function Get-Method() {
    $MethodValid = $False
    while ($MethodValid -ne $True) {
        $Method = Read-Host "HTTP Method"
        if ([Method]::IsDefined(([Method]), $Method.ToUpper())) {
            $MethodValid = $True
        }
        else { Write-Host "Invalid HTTP method." }
    }
    return $Method
}

# Ask for the URI for HTTP-Req to use
function Get-URI() {
    $URIValid = $False
    while ($URIValid -ne $True) {
        $URI = Read-Host "URI (and port)"
        $TestRequest = $null
        $TestRequest = (Invoke-WebRequest -URI $URI -TimeoutSec 3 -DisableKeepAlive)
        if ($TestRequest -eq $null) {
            $URIValid = $False
            Write-Host "Could not connect to host" 
        }
        else { $URIValid = $True }
    }
    return $URI
}

enum Info {
    Help    = 0
    Version = 1
}

enum Method {
    DEFAULT
    DELETE
    GET
    HEAD
    MERGE
    OPTIONS
    PATCH
    POST
    PUT
    TRACE
}

function Control-Menu() {
    $Quit = $False
    do {
        Write-Host "Pick an option:
    [1]: Modify HTTP Request (e.g. headers, body, etc)
    [2]: Run/Make Request
    [3]: Export Current Request Config
    [0]: Quit HTTP-Req"
        $Selection = Read-Host "Option"

        switch ($Selection) {
            "0" { $Quit = $True }
            "1" { ; }
            "2" { ; }
            "3" { ; }
            default { ; }
        }
    } while ($Quit -ne $True)
}

function Print-Info($type) {
    switch ($type) {
        Info.Version { $output = "http-req version 0.0.1" }
        Info.Help {
            $output = "usage: http-req [--version] [--import <filename.json>]
                [--help] [--usage]"
        }
    }
    Write-Host $output
}


switch ($operation) {
    ""          { Start-HTTP-Req }
    "--import"  { Import-Config $_ }
    "import"    { Import-Config $_ }
    "--version" { Print-Info Info.Version }
    "version"   { Print-Info Info.Version }
    default     { Print-Info Info.Help }
}

