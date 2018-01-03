using System;
using System.Collections.Generic;
using System.Text;

namespace Pong.Events
{

    public delegate void CourtRefresh(object sender, CourtRefreshEventArgs args);

    public class CourtRefreshEventArgs : EventArgs
    {
        public int MaxHeight { get; set; }
        public int MaxWidth { get; set; }
    }

}
