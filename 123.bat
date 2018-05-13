@ECHO OFF
reg add "HKEY_CLASSES_ROOT\*\shell\kindle"
reg add "HKEY_CLASSES_ROOT\*\shell\kindle" /v " " /d 上传到我的kindle
reg add "HKEY_CLASSES_ROOT\*\shell\kindle" /v Icon /d %~dp0kindle0.ico
reg add "HKEY_CLASSES_ROOT\*\shell\kindle\command" 
reg add "HKEY_CLASSES_ROOT\*\shell\kindle\command" /v " " /d "\"%~dp0kindleSend.exe\" \"%%1\"
reg add "HKEY_LOCAL_MACHINE\SOFTWARE\Classes\Folder\shell\kindle"
reg add "HKEY_LOCAL_MACHINE\SOFTWARE\Classes\Folder\shell\kindle" /v " " /d 上传到我的kindle
reg add "HKEY_LOCAL_MACHINE\SOFTWARE\Classes\Folder\shell\kindle" /v Icon /d %~dp0kindle0.ico
reg add "HKEY_LOCAL_MACHINE\SOFTWARE\Classes\Folder\shell\kindle\command" 
reg add "HKEY_LOCAL_MACHINE\SOFTWARE\Classes\Folder\shell\kindle\command" /v " " /d "\"%~dp0kindleSend.exe\" \"%%1\"
PAUSE