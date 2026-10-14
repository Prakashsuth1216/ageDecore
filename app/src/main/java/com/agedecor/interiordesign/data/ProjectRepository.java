package com.agedecor.interiordesign.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProjectRepository {

    public static List<ProjectModel> getAllProjects() {
        List<ProjectModel> list = new ArrayList<>();

        list.add(new ProjectModel(
                1,
                "The Celestia Sea-Facing Duplex",
                "Sea-Facing Luxury",
                "Worli Sea Face, Mumbai",
                3400,
                "₹52 Lakhs - ₹68 Lakhs (Turnkey)",
                "₹1,850 / sq.ft",
                90,
                "Italian Contemporary & Fluted Wood",
                "Panoramic Arabian Sea vista with imported Calacatta marble flooring, acoustic motorized drapery, brass inlaid wall accents, and bespoke Lutron ambient lighting.",
                Arrays.asList("Imported Calacatta Gold Marble", "Bespoke Italian Kitchen", "Acoustic Home Automation", "Ocean-Facing Terrace Bar")
        ));

        list.add(new ProjectModel(
                2,
                "The Minimalist Sanctuary Penthouse",
                "Penthouses",
                "Pali Hill, Bandra West, Mumbai",
                2600,
                "₹38 Lakhs - ₹48 Lakhs",
                "₹1,650 / sq.ft",
                75,
                "Japandi & Warm Minimalist",
                "Curated Scandinavian-Japanese aesthetic featuring micro-cement textures, concealed frameless doors, natural cane joinery, and warm recessed cove lighting.",
                Arrays.asList("Micro-Cement Seamless Flooring", "Zero-Edge Concealed Doors", "Custom White Ash Furniture", "Smart Climate Integration")
        ));

        list.add(new ProjectModel(
                3,
                "Skyline Horizon 3BHK Residence",
                "Modern 2/3 BHK",
                "Hiranandani Gardens, Powai, Mumbai",
                1650,
                "₹22 Lakhs - ₹29 Lakhs",
                "₹1,450 / sq.ft",
                55,
                "Modern Luxe & Space Optimization",
                "Designed for high-paced Mumbai living with intelligent space-saving partition walls, quartz-top island counter, tinted glass wardrobes, and concealed storage.",
                Arrays.asList("Modular German Hardware (Blum)", "Tinted Bronze Glass Wardrobes", "Acoustic Study Alcove", "Zero Dead-Space Engineering")
        ));

        list.add(new ProjectModel(
                4,
                "The Prestige Corporate Suite",
                "Executive Offices",
                "Bandra Kurla Complex (BKC), Mumbai",
                4200,
                "₹65 Lakhs - ₹85 Lakhs",
                "₹1,750 / sq.ft",
                60,
                "Industrial Elegance & Biophilic Office",
                "Flagship boardroom, executive pods, and living plant partitions engineered for high-profile business leaders in Mumbai's financial center.",
                Arrays.asList("Bespoke Acoustic Wall Panels", "Executive Marble Boardroom Table", "Biophilic Indoor Air Purification", "Commercial Grade HVAC Aesthetics")
        ));

        list.add(new ProjectModel(
                5,
                "Heritage Art-Deco Revival Apartment",
                "Heritage & Villas",
                "Marine Drive, South Mumbai",
                2100,
                "₹34 Lakhs - ₹44 Lakhs",
                "₹1,700 / sq.ft",
                70,
                "Art Deco Nostalgia & Modern Brass",
                "Preserving South Mumbai's iconic colonial architecture with restored arched doorways, geometric monochrome terrazzo, solid Burmese teakwood, and vintage brass chandeliers.",
                Arrays.asList("Authentic Custom Terrazzo", "Solid Burmese Teak Restoration", "Geometric Brass Grill Work", "High-Ceiling Ambient Chandeliers")
        ));

        list.add(new ProjectModel(
                6,
                "The Coastal Villa Estate",
                "Heritage & Villas",
                "Juhu Tara Road, Mumbai",
                4800,
                "₹75 Lakhs - ₹1.1 Crore",
                "₹1,950 / sq.ft",
                120,
                "Tropical Luxury Villa",
                "Sprawling private villa with indoor-outdoor water courts, travertine stone columns, private home theater, and temperature-controlled walk-in closets.",
                Arrays.asList("Indoor Zen Water Body", "Travertine Stone Cladding", "Dolby Atmos 9.2 Theater Room", "Private Terrace Lounge")
        ));

        return list;
    }

    public static List<String> getCategories() {
        return Arrays.asList("All", "Sea-Facing Luxury", "Penthouses", "Modern 2/3 BHK", "Executive Offices", "Heritage & Villas");
    }
}
