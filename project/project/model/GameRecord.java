package comp102x.project.model;

public class GameRecord {

	private String name;
	private int level;
	private int score;

	public GameRecord(String name, int level, int score) {
		super();
		this.name = name;
		this.level = level;
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
