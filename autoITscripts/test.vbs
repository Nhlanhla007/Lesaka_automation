

Option Explicit
 

 
    
    Dim objOutlook 
	Dim MyOutLookApp 
    Dim MyNameSpace 
    Dim MyFolder 
	Dim W
	
	Set W=WScript.CreateObject("Wscript.Shell")
    Set objOutlook = CreateObject("Outlook.Application")

    Dim objEmail
    Set objEmail = objOutlook.CreateItem(0)

    With objEmail
        .to = "deepa.mathias@police.wa.gov.au"
        .Subject = "This is a test message windows 10"
        .Body = "Hi there"
        .Display    
    End With
     Wscript.Sleep(1000)
	 W.SendKeys "%{s}"
   
     Wscript.Sleep(1000)

    Set objEmail = Nothing:    Set objOutlook = Nothing
	
	
	 Set MyOutLookApp = CreateObject("Outlook.Application")
    
    Set MyNameSpace = MyOutLookApp.GetNamespace("MAPI")
   

    Set MyFolder = MyNameSpace.GetDefaultFolder(6)
     
    MyFolder.Display
    
     Wscript.Sleep(30000)
    
	
	 W.SendKeys "{F9}"
    
     
     Wscript.Sleep(10000)
       
       Dim myItem 
	   
	 
       
       Set myItem = MyFolder.Items
	   
	
	 
	   Wscript.Sleep(10000)
	   
	    W.SendKeys "{UP}"
		Wscript.Sleep(1000)
		   W.SendKeys "{ENTER}"
		   	Wscript.Sleep(1000)
    
       MyOutLookApp.Quit
     

    Set MyFolder = Nothing
    Set MyNameSpace = Nothing
    Set MyOutLookApp = Nothing
     
	


		
	



