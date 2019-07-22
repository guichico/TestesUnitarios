package com.br.guilherme.entities;

import java.util.Date;

public class User {
	private Long id;
	private String name;	
	private String cpf;
	private Date birthDate;
	private Date registerDate;
	private Character gender;
	private String cep;
	private String city;
	private String district;
	private String address;
	private String addressNumber;
	private String addressComplement;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCpf() {
		return cpf;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public Character getGender() {
		return gender;
	}

	public String getCep() {
		return cep;
	}

	public String getCity() {
		return city;
	}

	public String getDistrict() {
		return district;
	}

	public String getAddress() {
		return address;
	}

	public String getAddressNumber() {
		return addressNumber;
	}

	public String getAddressComplement() {
		return addressComplement;
	}

	private User(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.cpf = builder.cpf;
		this.birthDate = builder.birthDate;
		this.registerDate = builder.registerDate;
		this.gender = builder.gender;
		this.cep = builder.cep;
		this.city = builder.city;
		this.district = builder.district;
		this.address = builder.address;
		this.addressNumber = builder.addressNumber;
		this.addressComplement = builder.addressComplement;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private Long id;
		private String name;
		private String cpf;
		private Date birthDate;
		private Date registerDate;
		private Character gender;
		private String cep;
		private String city;
		private String district;
		private String address;
		private String addressNumber;
		private String addressComplement;

		private Builder() {
		}

		public Builder withId(Long id) {
			this.id = id;
			return this;
		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Builder withCpf(String cpf) {
			this.cpf = cpf;
			return this;
		}

		public Builder withBirthDate(Date birthDate) {
			this.birthDate = birthDate;
			return this;
		}

		public Builder withRegisterDate(Date registerDate) {
			this.registerDate = registerDate;
			return this;
		}

		public Builder withGender(Character gender) {
			this.gender = gender;
			return this;
		}

		public Builder withCep(String cep) {
			this.cep = cep;
			return this;
		}

		public Builder withCity(String city) {
			this.city = city;
			return this;
		}

		public Builder withDistrict(String district) {
			this.district = district;
			return this;
		}

		public Builder withAddress(String address) {
			this.address = address;
			return this;
		}

		public Builder withAddressNumber(String addressNumber) {
			this.addressNumber = addressNumber;
			return this;
		}

		public Builder withAddressComplement(String addressComplement) {
			this.addressComplement = addressComplement;
			return this;
		}

		public User build() {
			return new User(this);
		}
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [name=" + name + "]";
	}
}