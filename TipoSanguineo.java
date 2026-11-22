package org.example.user;

public enum TipoSanguineo {
        A, B, AB, O;

        public boolean podeDoarPara(TipoSanguineo receptor) {

            if (this == O) {
                return true;
            }

            if (this == A) {
                return receptor == A || receptor == AB;
            }

            if (this == B) {
                return receptor == B || receptor == AB;
            }

            return receptor == AB;
        }
    }

