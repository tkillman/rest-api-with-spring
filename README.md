# rest-api-with-spring

CREATE TABLE EVENT(
	id NUMBER
	, name varchar2(100)
	, description varchar2(100)
	, begin_Enrollment_Date_Time DATE
	, close_Enrollment_Date_Time DATE
	, begin_Event_Date_Time DATE
	, end_Event_Date_Time DATE
	, location varchar2(100)
	, free varchar2(100)
	, off_Line varchar2(100)
	, base_Price NUMBER
	, max_Price NUMBER
	, limit_Of_Enrollment NUMBER
	, events_Status varchar2(100)
)
