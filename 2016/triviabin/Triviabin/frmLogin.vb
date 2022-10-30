Imports System.Data.OleDb 'Imports database communication module
Public Class frmLogin
    Public connstring As String = "Provider=Microsoft.ACE.OLEDB.12.0;Data Source=dbTriviabin.accdb" 'define location of database
    Public myConnection As New OleDbConnection(connstring) 'open db connection
    Public dr As OleDbDataReader 'setup the db datareader
    Dim sqlQuery As String

    Function dataWipe(cmd)  'function to wipe the data stored when it is no longer needed
        txtUser.Text = ""   'this also adds additonal security to the login screen code
        txtPasswd.Text = ""
        sqlQuery = ""
        cmd = New OleDbCommand()
        Return 0
    End Function

    Private Sub btnSignIn_Click(sender As Object, e As EventArgs) Handles btnSignIn.Click 'sign in button on click
        lblErrorMsg.Text = " " 'set error message to null
        sqlQuery = "SELECT * FROM tblUser WHERE [Password]=? AND Username=?" 'sql query to check the contents of the db
        myConnection.Open() 'open db connection
        Dim cmd01 As OleDbCommand = New OleDbCommand(sqlQuery, myConnection) ' Setup the sql command ready to be run
        cmd01.Parameters.AddWithValue("Password", txtPasswd.Text)   'add reqired parameters to the sql statement
        cmd01.Parameters.AddWithValue("Username", txtUser.Text)
        dr = cmd01.ExecuteReader    ' run the db reader
        If dr.Read() Then ' if the record exists then:
            myConnection.Close()    ' close db connection
            Me.Hide()   ' hide the current form
            frmHome.Show()  ' display the home page
            Dim varUser As String = txtUser.Text    ' store username before data wipe
            dataWipe(cmd01) ' call data wipe function (above)
            frmHome.lblWelcome.Text = "Hello " & varUser & ", Welcome To Triviabin" 'set the text in the welcome bar on the home screen
        Else
            myConnection.Close() ' close db connection
            lblErrorMsg.Text = "Incorrect Username or Password" ' display error message stating the problem
            dataWipe(cmd01) ' call the wipe data function
        End If
    End Sub
    Private Sub btnSignUp_Click(sender As Object, e As EventArgs) Handles btnSignUp.Click 'on signup button click
        lblErrorMsg.Text = " " 'null error message 
        sqlQuery = "SELECT * FROM tblUser WHERE Username=?" ' set the sql query to be run
        myConnection.Open() ' open db connection
        Dim cmd02 As OleDbCommand = New OleDbCommand(sqlQuery, myConnection) ' setup the sql command to be correctly run
        cmd02.Parameters.AddWithValue("Username", txtUser.Text) ' pass the required parameters to the sql statement
        dr = cmd02.ExecuteReader ' setup and run the sql command
        If dr.Read() Then ' if record exists and matches requirements
            myConnection.Close() ' close db connection
            dataWipe(cmd02) ' call the data wipe function
            lblErrorMsg.Text = "Username Already In Use" ' display error message in message label
        Else
            myConnection.Close() ' close db connection
            sqlQuery = "INSERT INTO tblUser (Username,[Password]) VALUES ('" & txtUser.Text & "','" & txtPasswd.Text & "')" 'sql to add new record to tblUsers
            Dim sqlQuery2 = "INSERT INTO tblProgress (Username) VALUES ('" & txtUser.Text & "')" 'define second sql query to add new record to tblProgress
            Dim cmd03 As OleDbCommand = New OleDbCommand(sqlQuery, myConnection) ' pass the parameters to the sql query
            Dim cmd04 As OleDbCommand = New OleDbCommand(sqlQuery2, myConnection)
            myConnection.Open() ' open db connection
            cmd03.ExecuteNonQuery() ' execute the sql commands
            cmd04.ExecuteNonQuery()
            myConnection.Close() ' close db connection
            Me.Hide() ' hide this form 
            Dim varUser As String = txtUser.Text ' backup the requested username 
            frmHome.lblWelcome.Text = "Hello " & varUser & ", Wecome To Triviabin" ' set welcome message on home screen
            frmHome.Show() ' display the home screen
            dataWipe(cmd03) ' call data wipe function
            sqlQuery2 = ""  ' null sqlquery
            cmd04 = New OleDbCommand()
        End If
    End Sub

    ' Close button script
    Private Sub btnClose_Click(sender As Object, e As EventArgs) Handles btnClose.Click
        Me.Close() ' close application 
    End Sub


    'script to allow the screen to be dragged around
    Private IsFormBeingDragged As Boolean = False
    Private MouseDownX As Integer
    Private MouseDownY As Integer

    Private Sub frmLogin_MouseDown(ByVal sender As Object, ByVal e As MouseEventArgs) Handles MyBase.MouseDown

        If e.Button = MouseButtons.Left Then
            IsFormBeingDragged = True
            MouseDownX = e.X
            MouseDownY = e.Y
        End If
    End Sub

    Private Sub frmLogin_MouseUp(ByVal sender As Object, ByVal e As MouseEventArgs) Handles MyBase.MouseUp

        If e.Button = MouseButtons.Left Then
            IsFormBeingDragged = False
        End If
    End Sub

    Private Sub frmLogin_MouseMove(ByVal sender As Object, ByVal e As MouseEventArgs) Handles MyBase.MouseMove

        If IsFormBeingDragged Then
            Dim temp As Point = New Point()

            temp.X = Me.Location.X + (e.X - MouseDownX)
            temp.Y = Me.Location.Y + (e.Y - MouseDownY)
            Me.Location = temp
            temp = Nothing
        End If
    End Sub
End Class
