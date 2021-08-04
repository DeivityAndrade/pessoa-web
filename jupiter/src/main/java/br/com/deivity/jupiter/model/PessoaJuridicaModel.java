package br.com.deivity.jupiter.model;

import java.util.Objects;
import java.util.SplittableRandom;

public class PessoaJuridicaModel extends PessoaModel {

		public PessoaJuridicaModel(String documento) {
			super(documento);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Boolean isDocumentoValido() {
			if (Objects.isNull(super.getDocumento()) 
					|| super.getDocumento().length() != 14) {
				return false;
			}
			String preDoc = super.getDocumento().substring(0, 7);
			String filial = super.getDocumento().substring(8, 12);
			String cnpjValido = this.gerarCnpj(preDoc, filial);
			return super.getDocumento().equals(cnpjValido);
		}

		@Override
		public String getNovoDocumento() {
			SplittableRandom sr = new SplittableRandom();
			String preDoc = "";
			for (int i = 0; i < 7; i++) {
				preDoc = preDoc + sr.nextInt(0, 10);
			}
			return this.gerarCnpj(preDoc, "0001");
		}

			@Override
			public String getDocumentoFormatado() {
				if (!this.isDocumentoValido()) {
					return this.getDocumento();
				}
				return this.getDocumento().replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
			}
			
			private String gerarCnpj(String preDocumento, String filial) {
				
				String preDoc = preDocumento + gerarNonoDigito(preDocumento) + filial;
				char[] numeros = preDoc.toCharArray();
				Integer soma = 0;
				
				int i = 9;
				for (int j = numeros.length - 1; j >= 0; j--) {
					Integer num = Integer.valueOf(String.valueOf(numeros[j]));
					soma += (num * i);
					i--;
					if (i < 2) {
						i = 9;
					}
				}
				Integer dv1 = soma % 11;
				
				preDoc = preDoc + (dv1.equals(10) ? 0 : dv1);
				numeros = preDoc.toCharArray();
				soma = 0;
				
				i = 9;
				for (int j = numeros.length - 1; j >= 0; j--) {
					Integer num = Integer.valueOf(String.valueOf(numeros[j]));
					soma += (num * i);
					i--;
					if (i < 2) {
						i = 9;
					}
				}
				Integer dv2 = soma % 11;
				
				return preDoc + (dv2.equals(10) ? 0 : dv2);
				
			}

			private String gerarNonoDigito(final String preDoc) {
				
				char[] numeros = preDoc.toCharArray();
				Integer soma = 0;
				
				int i = 2;
				for (char c : numeros) {
					Integer num = Integer.valueOf(String.valueOf(c)) * i;
					if (num >= 10) {
						num = ((num / 10) + (num % 10));
					}
					soma += num;
					i = (i == 2 ? 1 : 2);
				}
				
				return String.valueOf((((soma / 10) + 1) * 10) - soma);
			}
		}