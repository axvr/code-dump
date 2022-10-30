Public Class frmHome
    Private Sub btnQnMan_Click(sender As Object, e As EventArgs) Handles btnQnMan.Click
        Me.Hide()
        frmQnMan.Show()
    End Sub

    Private Sub btnAnsQns_Click(sender As Object, e As EventArgs) Handles btnAnsQns.Click
        Me.Hide()
        frmAnsQns.Show()
    End Sub

    Private Sub btnProgress_Click(sender As Object, e As EventArgs) Handles btnProgress.Click
        Me.Hide()
        frmProgress.Show()
    End Sub

    Private Sub btnBack_Click(sender As Object, e As EventArgs) Handles btnBack.Click
        lblWelcome.Text = " "
        Me.Close()
        frmLogin.Show()
    End Sub

    Private Sub btnClose_Click(sender As Object, e As EventArgs) Handles btnClose.Click
        Me.Close()
        frmLogin.Close()
    End Sub

    Private IsFormBeingDragged As Boolean = False
    Private MouseDownX As Integer
    Private MouseDownY As Integer

    Private Sub frmHome_MouseDown(ByVal sender As Object, ByVal e As MouseEventArgs) Handles MyBase.MouseDown

        If e.Button = MouseButtons.Left Then
            IsFormBeingDragged = True
            MouseDownX = e.X
            MouseDownY = e.Y
        End If
    End Sub

    Private Sub frmHome_MouseUp(ByVal sender As Object, ByVal e As MouseEventArgs) Handles MyBase.MouseUp

        If e.Button = MouseButtons.Left Then
            IsFormBeingDragged = False
        End If
    End Sub

    Private Sub frmHome_MouseMove(ByVal sender As Object, ByVal e As MouseEventArgs) Handles MyBase.MouseMove

        If IsFormBeingDragged Then
            Dim temp As Point = New Point()

            temp.X = Me.Location.X + (e.X - MouseDownX)
            temp.Y = Me.Location.Y + (e.Y - MouseDownY)
            Me.Location = temp
            temp = Nothing
        End If
    End Sub
End Class