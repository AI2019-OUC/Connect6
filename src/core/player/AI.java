package core.player;

public abstract class AI extends Player {

	public AI() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 是否为人工下棋，true代表人工下棋，false代表机器人下棋
	 */
	@Override
	public final boolean isManual() {
		// TODO Auto-generated method stub
		return false;
	}
}
