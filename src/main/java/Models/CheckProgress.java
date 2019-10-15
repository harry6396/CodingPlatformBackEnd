package Models;

public class CheckProgress
{
	public String teamName;
	public String questionType;
	public String questionNumber;
	public String status;
	
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public String getTeamName()
	{
		return teamName;
	}
	public void setTeamName(String teamName)
	{
		this.teamName = teamName;
	}
	public String getQuestoinType()
	{
		return questionType;
	}
	public void setQuestoinType(String questoinType)
	{
		this.questionType = questoinType;
	}
	public String getQuestionNumber()
	{
		return questionNumber;
	}
	public void setQuestionNumber(String questionNumber)
	{
		this.questionNumber = questionNumber;
	}
	
}
