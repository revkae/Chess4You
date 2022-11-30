package me.raven.Engine.Utils;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

public class Vertex {

    public Vector3f position;
    public Vector4f color;
    public Vector2f texCoord;
    public float texID;

    public Vertex(Vector3f position, Vector4f color, Vector2f texCoord, float texID) {
        this.position = position;
        this.color = color;
        this.texCoord = texCoord;
        this.texID = texID;
    }

    public static float[] createQuad(Vector3f position, Vector4f color, Vector2f scale, float texID) {
        //List<Vertex> temp = new ArrayList<>();

        float[] output = new float[40];

        output[0] = position.x;
        output[1] = position.y;
        output[2] = position.z;
        output[3] = color.x;
        output[4] = color.y;
        output[5] = color.z;
        output[6] = color.w;
        output[7] = 0.0f;
        output[8] = 0.0f;
        output[9] = texID;

        output[10] = position.x + scale.x;
        output[11] = position.y;
        output[12] = position.z;
        output[13] = color.x;
        output[14] = color.y;
        output[15] = color.z;
        output[16] = color.w;
        output[17] = 1.0f;
        output[18] = 0.0f;
        output[19] = texID;

        output[20] = position.x + scale.x;
        output[21] = position.y + scale.y;
        output[22] = position.z;
        output[23] = color.x;
        output[24] = color.y;
        output[25] = color.z;
        output[26] = color.w;
        output[27] = 1.0f;
        output[28] = 1.0f;
        output[29] = texID;

        output[30] = position.x;
        output[31] = position.y + scale.y;
        output[32] = position.z;
        output[33] = color.x;
        output[34] = color.y;
        output[35] = color.z;
        output[36] = color.w;
        output[37] = 0.0f;
        output[38] = 1.0f;
        output[39] = texID;

//        temp.add(new Vertex(
//                new Vector3f(position.x, position.y, position.z),
//                color,
//                new Vector2f(0.0f, 0.0f),
//                texID));
//        temp.add(new Vertex(
//                new Vector3f(position.x + size, position.y, position.z),
//                new Vector4f(1.f, 1.f, 1.f, 1.f),
//                new Vector2f(1.0f, 0.0f),
//                texID));
//        temp.add(new Vertex(
//                new Vector3f(position.x + size,  position.y + size, position.z),
//                color,
//                new Vector2f(1.f, 1.f),
//                texID));
//        temp.add(new Vertex(
//                new Vector3f(position.x, position.y + size, position.z),
//                color,
//                new Vector2f(0.0f, 1.f),
//                texID));
//
//        float[] output = new float[40];
//        int num = 10;
//
//        for (int i = 0; i < temp.size(); i++) {
//            output[0 + num * i] = temp.get(i).position.x;
//            output[1 + num * i] = temp.get(i).position.y;
//            output[2 + num * i] = temp.get(i).position.z;
//            output[3 + num * i] = temp.get(i).color.x;
//            output[4 + num * i] = temp.get(i).color.y;
//            output[5 + num * i] = temp.get(i).color.z;
//            output[6 + num * i] = temp.get(i).color.w;
//            output[7 + num * i] = temp.get(i).texCoord.x;
//            output[8 + num * i] = temp.get(i).texCoord.y;
//            output[9 + num * i] = temp.get(i).texID;
//        }

        return output;
    }

    public static int quadByteSize() {
        return 4 *(Float.BYTES * 3 + Float.BYTES * 4 + Float.BYTES * 2 + Float.BYTES);
    }

    public static int byteSize() {
        return Float.BYTES * 3 + Float.BYTES * 4 + Float.BYTES * 2 + Float.BYTES;
    }

    public static int positionOffsetByte() {
        return 0;
    }

    public static int colorOffsetByte() {
        return Float.BYTES * 3;
    }

    public static int texCoordOffsetByte() {
        return Float.BYTES * 3 + Float.BYTES * 4;
    }

    public static int texIDOffsetByte() {
        return Float.BYTES * 3 + Float.BYTES * 4 + Float.BYTES * 2;
    }
}
