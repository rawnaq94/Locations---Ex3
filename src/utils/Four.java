package utils;

public class Four<L, R> {

	private final L left;
	private final R right1;
	private final R right2;
	private final R right3;

	public Four(L left, R right1, R right2, R right3) {
		this.left = left;
		this.right1 = right1;
		this.right2 = right2;
		this.right3 = right3;
	}

	public L getLeft() {
		return left;
	}

	public R getRight1() {
		return right1;
	}

	public R getRight2() {
		return right2;
	}

	public R getRight3() {
		return right3;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((right1 == null) ? 0 : right1.hashCode());
		result = prime * result + ((right2 == null) ? 0 : right2.hashCode());
		result = prime * result + ((right3 == null) ? 0 : right3.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Four other = (Four) obj;
		if (left == null) {
			if (other.left != null)
				return false;
		} else if (!left.equals(other.left))
			return false;
		if (right1 == null) {
			if (other.right1 != null)
				return false;
		} else if (!right1.equals(other.right1))
			return false;
		if (right2 == null) {
			if (other.right2 != null)
				return false;
		} else if (!right2.equals(other.right2))
			return false;
		if (right3 == null) {
			if (other.right3 != null)
				return false;
		} else if (!right3.equals(other.right3))
			return false;
		return true;
	}

}