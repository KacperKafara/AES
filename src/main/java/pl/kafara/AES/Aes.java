package pl.kafara.AES;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Aes {

    private int Nr;
    private int Nk;

    private final int[] sBox = {
            //0    1     2     3     4     5     6     7     8     9     A     B     C     D     E     F
            0x63, 0x7C, 0x77, 0x7B, 0xF2, 0x6B, 0x6F, 0xC5, 0x30, 0x01, 0x67, 0x2B, 0xFE, 0xD7, 0xAB, 0x76,
            0xCA, 0x82, 0xC9, 0x7D, 0xFA, 0x59, 0x47, 0xF0, 0xAD, 0xD4, 0xA2, 0xAF, 0x9C, 0xA4, 0x72, 0xC0,
            0xB7, 0xFD, 0x93, 0x26, 0x36, 0x3F, 0xF7, 0xCC, 0x34, 0xA5, 0xE5, 0xF1, 0x71, 0xD8, 0x31, 0x15,
            0x04, 0xC7, 0x23, 0xC3, 0x18, 0x96, 0x05, 0x9A, 0x07, 0x12, 0x80, 0xE2, 0xEB, 0x27, 0xB2, 0x75,
            0x09, 0x83, 0x2C, 0x1A, 0x1B, 0x6E, 0x5A, 0xA0, 0x52, 0x3B, 0xD6, 0xB3, 0x29, 0xE3, 0x2F, 0x84,
            0x53, 0xD1, 0x00, 0xED, 0x20, 0xFC, 0xB1, 0x5B, 0x6A, 0xCB, 0xBE, 0x39, 0x4A, 0x4C, 0x58, 0xCF,
            0xD0, 0xEF, 0xAA, 0xFB, 0x43, 0x4D, 0x33, 0x85, 0x45, 0xF9, 0x02, 0x7F, 0x50, 0x3C, 0x9F, 0xA8,
            0x51, 0xA3, 0x40, 0x8F, 0x92, 0x9D, 0x38, 0xF5, 0xBC, 0xB6, 0xDA, 0x21, 0x10, 0xFF, 0xF3, 0xD2,
            0xCD, 0x0C, 0x13, 0xEC, 0x5F, 0x97, 0x44, 0x17, 0xC4, 0xA7, 0x7E, 0x3D, 0x64, 0x5D, 0x19, 0x73,
            0x60, 0x81, 0x4F, 0xDC, 0x22, 0x2A, 0x90, 0x88, 0x46, 0xEE, 0xB8, 0x14, 0xDE, 0x5E, 0x0B, 0xDB,
            0xE0, 0x32, 0x3A, 0x0A, 0x49, 0x06, 0x24, 0x5C, 0xC2, 0xD3, 0xAC, 0x62, 0x91, 0x95, 0xE4, 0x79,
            0xE7, 0xC8, 0x37, 0x6D, 0x8D, 0xD5, 0x4E, 0xA9, 0x6C, 0x56, 0xF4, 0xEA, 0x65, 0x7A, 0xAE, 0x08,
            0xBA, 0x78, 0x25, 0x2E, 0x1C, 0xA6, 0xB4, 0xC6, 0xE8, 0xDD, 0x74, 0x1F, 0x4B, 0xBD, 0x8B, 0x8A,
            0x70, 0x3E, 0xB5, 0x66, 0x48, 0x03, 0xF6, 0x0E, 0x61, 0x35, 0x57, 0xB9, 0x86, 0xC1, 0x1D, 0x9E,
            0xE1, 0xF8, 0x98, 0x11, 0x69, 0xD9, 0x8E, 0x94, 0x9B, 0x1E, 0x87, 0xE9, 0xCE, 0x55, 0x28, 0xDF,
            0x8C, 0xA1, 0x89, 0x0D, 0xBF, 0xE6, 0x42, 0x68, 0x41, 0x99, 0x2D, 0x0F, 0xB0, 0x54, 0xBB, 0x16 };

    private final int[] inv_sbox = {
            0x52, 0x09, 0x6A, 0xD5, 0x30, 0x36, 0xA5, 0x38, 0xBF, 0x40, 0xA3, 0x9E, 0x81, 0xF3, 0xD7, 0xFB,
            0x7C, 0xE3, 0x39, 0x82, 0x9B, 0x2F, 0xFF, 0x87, 0x34, 0x8E, 0x43, 0x44, 0xC4, 0xDE, 0xE9, 0xCB,
            0x54, 0x7B, 0x94, 0x32, 0xA6, 0xC2, 0x23, 0x3D, 0xEE, 0x4C, 0x95, 0x0B, 0x42, 0xFA, 0xC3, 0x4E,
            0x08, 0x2E, 0xA1, 0x66, 0x28, 0xD9, 0x24, 0xB2, 0x76, 0x5B, 0xA2, 0x49, 0x6D, 0x8B, 0xD1, 0x25,
            0x72, 0xF8, 0xF6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xD4, 0xA4, 0x5C, 0xCC, 0x5D, 0x65, 0xB6, 0x92,
            0x6C, 0x70, 0x48, 0x50, 0xFD, 0xED, 0xB9, 0xDA, 0x5E, 0x15, 0x46, 0x57, 0xA7, 0x8D, 0x9D, 0x84,
            0x90, 0xD8, 0xAB, 0x00, 0x8C, 0xBC, 0xD3, 0x0A, 0xF7, 0xE4, 0x58, 0x05, 0xB8, 0xB3, 0x45, 0x06,
            0xD0, 0x2C, 0x1E, 0x8F, 0xCA, 0x3F, 0x0F, 0x02, 0xC1, 0xAF, 0xBD, 0x03, 0x01, 0x13, 0x8A, 0x6B,
            0x3A, 0x91, 0x11, 0x41, 0x4F, 0x67, 0xDC, 0xEA, 0x97, 0xF2, 0xCF, 0xCE, 0xF0, 0xB4, 0xE6, 0x73,
            0x96, 0xAC, 0x74, 0x22, 0xE7, 0xAD, 0x35, 0x85, 0xE2, 0xF9, 0x37, 0xE8, 0x1C, 0x75, 0xDF, 0x6E,
            0x47, 0xF1, 0x1A, 0x71, 0x1D, 0x29, 0xC5, 0x89, 0x6F, 0xB7, 0x62, 0x0E, 0xAA, 0x18, 0xBE, 0x1B,
            0xFC, 0x56, 0x3E, 0x4B, 0xC6, 0xD2, 0x79, 0x20, 0x9A, 0xDB, 0xC0, 0xFE, 0x78, 0xCD, 0x5A, 0xF4,
            0x1F, 0xDD, 0xA8, 0x33, 0x88, 0x07, 0xC7, 0x31, 0xB1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xEC, 0x5F,
            0x60, 0x51, 0x7F, 0xA9, 0x19, 0xB5, 0x4A, 0x0D, 0x2D, 0xE5, 0x7A, 0x9F, 0x93, 0xC9, 0x9C, 0xEF,
            0xA0, 0xE0, 0x3B, 0x4D, 0xAE, 0x2A, 0xF5, 0xB0, 0xC8, 0xEB, 0xBB, 0x3C, 0x83, 0x53, 0x99, 0x61,
            0x17, 0x2B, 0x04, 0x7E, 0xBA, 0x77, 0xD6, 0x26, 0xE1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0C, 0x7D };

    private byte[][] generateKeyState(byte[] key) throws Exception {
        byte[][] result;
        if (key.length == 16) {
            result = new byte[Nk][4];
        } else if (key.length == 24) {
            result = new byte[Nk][4];
        } else if (key.length == 32) {
            result = new byte[Nk][4];
        } else {
            throw new Exception("Zla dlugosc klucza!");
        }
        int i = 0;
        int j = 0;
        while (i < Nk) {
            result[i][0] = key[j];
            result[i][1] = key[++j];
            result[i][2] = key[++j];
            result[i][3] = key[++j];
            i++;
            j++;
        }

        return result;
    }

    private byte[][] addRoundKey(byte[][] state, byte[][] key, int round) {
        byte[][] result = new byte[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = (byte) (state[i][j] ^ key[round * 4 + i][j]);
            }
        }
        return result;
    }

    private byte[] xor(byte[] message, byte[] key) {
        byte[] xor = new byte[message.length];
        for(int i = 0; i < message.length; i++) {
            xor[i] = (byte) (message[i] ^ key[i]);
        }
        return xor;
    }

    public byte[][] keyExpansion(byte[][] key) {
        byte[] rcon = {(byte)0x01, (byte)0x02, (byte)0x04, (byte)0x08, (byte)0x10, (byte)0x20, (byte)0x40, (byte)0x80, (byte)0x1B, (byte)0x36};
        byte[] temp;
        byte[][] newKeyState = new byte[4 * (Nr + 1)][4];
        for (int i = 0; i < key.length; i++) {
            newKeyState[i] = key[i];
        }
        for (int i = Nk; i < 4 * (Nr - 1); i++) {
            temp = newKeyState[i - 1];
            if (i % Nk == 0) {
                byte tmp = temp[0];
                temp[0] = temp[1];
                temp[1] = temp[2];
                temp[2] = temp[3];
                temp[3] = tmp;
                for (int j = 0; j < 4; j++) {
                    temp[j] = subByte(temp[j], sBox);
                }
                temp[0] = (byte) (temp[0] ^ rcon[i / Nk]);
            } else if(Nk > 6 && i % Nk == 4) {
                for (int j = 0; j < 4; j++) {
                    temp[j] = subByte(temp[j], sBox);
                }
            }
            newKeyState[i] = xor(newKeyState[i - Nk], temp);
        }
        return newKeyState;
    }

    private byte subByte(byte b, int[] tab) {
        int row = (b >> 4) & 0x0f;
        int col = b & 0x0f;
        return (byte) tab[row * 16 + col];
    }

    public byte[][] subBytes(byte[][] state, int[] tab) {
        byte[][] result = new byte[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = (byte) (subByte(state[i][j], tab) & 0xFF);
            }
        }
        return result;
    }

    private byte[][] shiftRows(byte[][] state) {
        byte[][] result = new byte[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = state[i][(j + i) % 4];
            }
        }
        return result;
    }

    private byte[][] invShiftRows(byte[][] state) {
        byte[][] result = new byte[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = state[i][(j - i + 4) % 4];
            }
        }
        return result;
    }

    private byte[][] mixColumns(byte[][] state) {
        byte[] result = new byte[4];
        byte b01 = (byte)0x01;
        byte b02 = (byte)0x02;
        byte b03 = (byte)0x03;
        for (int i = 0; i < 4; i++) {
            result[0] = (byte) (mul(b02, state[0][i]) ^ mul(b03, state[1][i]) ^ mul(b01, state[2][i]) ^ mul(b01, state[3][i]));
            result[1] = (byte) (mul(b01, state[0][i]) ^ mul(b02, state[1][i]) ^ mul(b03, state[2][i]) ^ mul(b01, state[3][i]));
            result[2] = (byte) (mul(b01, state[0][i]) ^ mul(b01, state[1][i]) ^ mul(b02, state[2][i]) ^ mul(b03, state[3][i]));
            result[3] = (byte) (mul(b03, state[0][i]) ^ mul(b01, state[1][i]) ^ mul(b01, state[2][i]) ^ mul(b02, state[3][i]));
            for(int j = 0; j < 4; j++) {
                state[j][i] = result[j];
            }
        }
        return state;
    }

    private byte[][] invMixColumns(byte[][] state) {
        byte[] result = new byte[4];
        byte b0e = (byte)0x0e;
        byte b0b = (byte)0x0b;
        byte b0d = (byte)0x0d;
        byte b09 = (byte)0x09;
        for (int i = 0; i < 4; i++) {
            result[0] = (byte) (mul(b0e, state[0][i]) ^ mul(b0b, state[1][i]) ^ mul(b0d, state[2][i]) ^ mul(b09, state[3][i]));
            result[1] = (byte) (mul(b09, state[0][i]) ^ mul(b0e, state[1][i]) ^ mul(b0b, state[2][i]) ^ mul(b0d, state[3][i]));
            result[2] = (byte) (mul(b0d, state[0][i]) ^ mul(b09, state[1][i]) ^ mul(b0e, state[2][i]) ^ mul(b0b, state[3][i]));
            result[3] = (byte) (mul(b0b, state[0][i]) ^ mul(b0d, state[1][i]) ^ mul(b09, state[2][i]) ^ mul(b0e, state[3][i]));
            for(int j = 0; j < 4; j++) {
                state[j][i] = result[j];
            }
        }
        return state;
    }

    private byte mul(byte a, byte b) {
        byte aa = a, bb = b, r = 0, t;
        while (aa != 0)
        {
            if ((aa & 1) != 0)
                r = (byte) (r ^ bb);
            t = (byte) (bb & 0x20);
            bb = (byte) (bb << 1);
            if (t != 0)
                bb = (byte) (bb ^ 0x1b);
            aa = (byte) ((aa & 0xff) >> 1);
        }
        return r;
    }

    private byte[] encrypt(byte[] block, byte[][] keyState) {
        byte[][] state = new byte[4][4];
        byte[] result = new byte[16];
        for (int i = 0; i < 4; i++) {
            System.arraycopy(block, i * 4, state[i], 0, 4);
        }
        keyState = keyExpansion(keyState);
        state = addRoundKey(state, keyState, 0);
        for (int i = 1; i < this.Nr; i++) {
            state = subBytes(state, sBox);
            state = shiftRows(state);
            state = mixColumns(state);
            state = addRoundKey(state, keyState, i);
        }
        state = subBytes(state, sBox);
        state = shiftRows(state);
        state = addRoundKey(state, keyState, Nr);
        for (int i = 0; i < 4; i++) {
            System.arraycopy(state[i], 0, result, i * 4, 4);
        }
        return result;
    }

    private byte[] decrypt(byte[] block, byte[][] keyState) {
        byte[][] state = new byte[4][4];
        byte[] result = new byte[16];
        for (int i = 0; i < 4; i++) {
            System.arraycopy(block, i * 4, state[i], 0, 4);
        }
        keyState = keyExpansion(keyState);
        state = addRoundKey(state, keyState, 0);
        for (int i = 1; i < this.Nr; i++) {
            state = invShiftRows(state);
            state = subBytes(state, inv_sbox);
            state = addRoundKey(state, keyState, i);
            state = invMixColumns(state);
        }
        state = invShiftRows(state);
        state = subBytes(state, inv_sbox);
        state = addRoundKey(state, keyState, Nr);
        for (int i = 0; i < 4; i++) {
            System.arraycopy(state[i], 0, result, i * 4, 4);
        }
        return result;
    }

    public List<Byte> encode(byte[] msg, byte[] key) throws Exception {
        List<Byte> filledMsg = new ArrayList<>();
        List<Byte> result = new ArrayList<>();
        this.Nk = key.length / 4;
        byte[][] keyState = generateKeyState(key);
        this.Nr = Nk + 6;
        for (byte b : msg) {
            filledMsg.add(b);
        }
        while (filledMsg.size() % 16 != 0) {
            filledMsg.add((byte) 0);
        }
        byte[] block = new byte[16];
        for (int i = 0; i < filledMsg.size();) {
            for (int j = 0; j < 16; j++) {
                block[j] = filledMsg.get(i++);
            }
            block = encrypt(block, keyState);
            for (int j = 0; j < 16; j++) {
                result.add(block[j]);
            }
        }
        return result;
    }

    public List<Byte> decode(byte[] msg, byte[] key) throws Exception {
        List<Byte> filledMsg = new ArrayList<>();
        List<Byte> result = new ArrayList<>();
        byte[][] keyState = generateKeyState(key);
        for (byte b : msg) {
            filledMsg.add(b);
        }
        byte[] block = new byte[16];
        for (int i = 0; i < filledMsg.size();) {
            for (int j = 0; j < 16; j++) {
                block[j] = filledMsg.get(i++);
            }
            block = decrypt(block, keyState);
            for (int j = 0; j < 16; j++) {
                result.add(block[j]);
            }
        }
        for(int i = 0; i < result.size(); i++) {
            if (result.get(i) == 0) {
                result.remove(i);
            }
        }
        return result;
    }
}
