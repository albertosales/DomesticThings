package com.sales.domesticthings.vo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Empregado implements Parcelable {

	private int id;
	private String nome;
	private Date aniversario;
	private double salario;

	public Empregado() {

		super();
	}

	private Empregado(Parcel in) {
		super();
		this.id = in.readInt();
		this.nome = in.readString();
		this.aniversario = new Date(in.readLong());
		this.salario = in.readDouble();
	}

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public String getNome() {

		return nome;
	}

	public void setNome(String nome) {

        this.nome = nome;
	}

	public Date getAniversario() {

		return aniversario;
	}

	public void setAniversario(Date aniversario) {

		this.aniversario = aniversario;
	}

	public double getSalario() {

		return salario;
	}

	public void setSalario(double salario) {

		this.salario = salario;
	}

	@Override
	public String toString() {
		return "Empregado [id=" + id + ", nome=" + nome + ", aniversario="
				+ aniversario + ", salario=" + salario + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Empregado other = (Empregado) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeInt(getId());
		parcel.writeString(getNome());
		parcel.writeLong(getAniversario().getTime());
		parcel.writeDouble(getSalario());
	}

	public static final Creator<Empregado> CREATOR = new Creator<Empregado>() {
		public Empregado createFromParcel(Parcel in) {
			return new Empregado(in);
		}

		public Empregado[] newArray(int size) {

            return new Empregado[size];
		}
	};

}
