package academy.affineCalculation;

import static academy.utils.ConstantsConfig.C;
import static academy.utils.ConstantsConfig.F;

import academy.dto.DoublePoint;
import academy.dto.inputs.TransformFunctionsEnum;

public class CalculatingTransformFunctions {

    public DoublePoint calculateFunction(DoublePoint doublePoint, TransformFunctionsEnum function) {
        return switch (function) {
            case LINEAR -> calculateLinearFunction(doublePoint);
            case SINUSOIDAL -> calculateSinusoidalFunction(doublePoint);
            case SWIRL -> calculateSwirlFunction(doublePoint);
            case SPHERICAL -> calculateSphericalFunction(doublePoint);
            case HORSESHOE -> calculateHorseshoeFunction(doublePoint);
            case POPCORN -> calculatePopcornFunction(doublePoint);
        };
    }

    private DoublePoint calculateSwirlFunction(DoublePoint doublePoint) {
        double r = doublePoint.x() * doublePoint.x() + doublePoint.y() * doublePoint.y();
        double sinR2 = Math.sin(r * r);
        double cosR2 = Math.cos(r * r);
        double x = doublePoint.x() * sinR2 - doublePoint.y() * cosR2;
        double y = doublePoint.x() * cosR2 + doublePoint.y() * sinR2;
        return new DoublePoint(x, y, doublePoint.color());
    }

    private DoublePoint calculateHorseshoeFunction(DoublePoint doublePoint) {
        double r = Math.sqrt(doublePoint.x() * doublePoint.x() + doublePoint.y() * doublePoint.y());
        if (r == 0) return new DoublePoint(0, 0, doublePoint.color());
        double x = (doublePoint.x() - doublePoint.y()) * (doublePoint.x() + doublePoint.y()) / r;
        double y = 2 * doublePoint.x() * doublePoint.y() / r;
        return new DoublePoint(x, y, doublePoint.color());
    }

    private DoublePoint calculateLinearFunction(DoublePoint doublePoint) {
        return doublePoint;
    }

    private DoublePoint calculateSinusoidalFunction(DoublePoint doublePoint) {
        double x = Math.sin(doublePoint.x());
        double y = Math.sin(doublePoint.y());
        return new DoublePoint(x, y, doublePoint.color());
    }

    private DoublePoint calculateSphericalFunction(DoublePoint doublePoint) {
        double r = Math.sqrt(doublePoint.x() * doublePoint.x() + doublePoint.y() * doublePoint.y());
        if (r == 0) {
            return new DoublePoint(0, 0, doublePoint.color());
        }
        double factor = 1.0 / (r * r);
        double x = doublePoint.x() * factor;
        double y = doublePoint.y() * factor;
        return new DoublePoint(x, y, doublePoint.color());
    }

    private DoublePoint calculatePopcornFunction(DoublePoint doublePoint) {
        double c = C;
        double f = F;
        double x = doublePoint.x() + c * Math.sin(Math.tan(3 * doublePoint.y()));
        double y = doublePoint.y() + f * Math.sin(Math.tan(3 * doublePoint.x()));
        return new DoublePoint(x, y, doublePoint.color());
    }
}
